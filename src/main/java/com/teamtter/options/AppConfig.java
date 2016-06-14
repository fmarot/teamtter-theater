package com.teamtter.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfig {

	private static final String	APP_PROPERTIES_FILENAME	= "app.properties";

	private Properties			properties;

	private String	applicationName;

	public AppConfig(String[] args, String applicationName) throws Exception {
		this.applicationName = applicationName;
		properties = loadProperties(args);
	}

	public <T> T getConfigBean(Class<T> clazz) throws Exception {
		T bean = clazz.newInstance();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String sKey = (String) entry.getKey();
			String sValue = (String) entry.getValue();
			tryToAssignValueAFieldInTheBean(sKey, sValue, bean);
		}
		return bean;
	}

	private Properties loadProperties(String[] args) throws Exception {
		Properties prop = new Properties();

		// load from the jar
		try (InputStream toCloseInput = this.getClass().getResourceAsStream("/" + APP_PROPERTIES_FILENAME)) {
			prop.load(toCloseInput);
		}

		// override with content of local file
		File fileSystemConfigFile = new File(APP_PROPERTIES_FILENAME);
		if (fileSystemConfigFile.exists()) {
			try (InputStream input = new FileInputStream(APP_PROPERTIES_FILENAME)) {
				prop.load(input);
			}
		}

		// override with content in file in user's $HOME/.applicationName/APP_PROPERTIES_FILENAME
		String homePath = System.getProperty("user.home");
		File appFolderInUserHome = new File(homePath, "." + applicationName);
		File userSpecificConfig = new File(appFolderInUserHome, APP_PROPERTIES_FILENAME);
		if (userSpecificConfig.exists()) {
			try (InputStream input = new FileInputStream(APP_PROPERTIES_FILENAME)) {
				prop.load(input);
			}
		}

		// override with cmd-line args
		StringBuilder sb = new StringBuilder();
		for (String arg : args) {
			if (arg.contains("=") && arg.startsWith("--")) {
				String rawArg = arg.substring(2, arg.length()); // without the
																// leading '--'
				sb.append(rawArg).append("\n");
			}
		}
		String commandlineProperties = sb.toString();
		if (!commandlineProperties.isEmpty()) {
			prop.load(new StringReader(commandlineProperties));
		}

		return prop;
	}

	private <T> void tryToAssignValueAFieldInTheBean(String sKey, String sValue, T bean) {
		Class<? extends Object> beanClass = bean.getClass();
		try {
			Field declaredField = beanClass.getDeclaredField(sKey);
			declaredField.setAccessible(true);
			Object typedValue = convertValueToFieldType(sValue, declaredField);
			declaredField.set(bean, typedValue);
			log.info("Option {} set on bean {}", sKey, beanClass.getSimpleName());
		} catch (Exception e) {
			log.trace("Unable to assign {} to bean {}", sKey, beanClass.getSimpleName());
		}
	}

	private Object convertValueToFieldType(String sValue, Field field) {
		Class<?> fieldClass = field.getType();
		Object result = null;
		if (fieldClass == String.class) {
			result = sValue;
		} else if (fieldClass == int.class) {
			result = Integer.parseInt(sValue);
		} else if (fieldClass == Integer.class) {
			result = Integer.parseInt(sValue);
		} else if (fieldClass == File.class) {
			result = new File(sValue);
		} else {
			log.warn("Unhandled parameter conversion {} into {}", sValue, field);
		}
		return result;
	}

}
