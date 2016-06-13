package com.teamtter.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfig {
	private static final String APP_PROPERTIES_FILENAME = "app.properties";

	private Map<Class<?>, Object> beans = new HashMap<>();
	private String[] args;

	public AppConfig(String[] args, Class<?>... configClasses) throws Exception {
		this.args = args;

		for (Class<?> o : configClasses) {
			try {
				Object bean = o.newInstance();
				beans.put(o, bean);
			} catch (Exception e) {
				log.error("Error instantiating {}", o, e);
			}
		}
		load();
	}

	public <T> T getConfigBean(Class<T> clazz) {
		return (T) beans.get(clazz);
	}

	private Properties loadProperties() throws Exception {
		Properties prop = new Properties();

		// load from the jar
		try (InputStream toCloseInput = this.getClass().getResourceAsStream("/" + APP_PROPERTIES_FILENAME)) {
			prop.load(toCloseInput);
		}

		// override with content of local file
		InputStream input = null;
		File fileSystemConfigFile = new File(APP_PROPERTIES_FILENAME);
		if (fileSystemConfigFile.exists()) {
			try (InputStream input2 = new FileInputStream(APP_PROPERTIES_FILENAME)) {
				prop.load(input2);
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

	private void load() throws Exception {
		Properties properties = loadProperties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String sKey = (String) entry.getKey();
			String sValue = (String) entry.getValue();
			assignValueToFieldInOneOfTheBeans(sKey, sValue, beans);
		}
	}

	private void assignValueToFieldInOneOfTheBeans(String sKey, String sValue, Map<Class<?>, Object> beans) {
		boolean oneFieldSet = false;
		for (Entry<Class<?>, Object> entry : beans.entrySet()) {
			try {
				Class<?> clazz = entry.getKey();
				Object bean = entry.getValue();
				Field declaredField = clazz.getDeclaredField(sKey);
				declaredField.setAccessible(true);
				Object typedValue = convertValueToFieldType(sValue, declaredField);
				declaredField.set(bean, typedValue);
				log.info("Option {} set on bean {}", sKey, clazz.getSimpleName());
				oneFieldSet = true;
				break;
			} catch (Exception e) {
				log.trace("", e);
			}
		}
		if (!oneFieldSet) {
			log.warn("Unable to find matching bean class for option {}={}", sKey, sValue);
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
