package com.teamtter.theatre;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;




@Slf4j
public class AppConfig {
	
	private static final String APP_PROPERTIES_FILENAME = "app.properties";
	
	public static final String DVD_PATH = "dvd.path";
	public static final String DVD_TITLE = "start.with.dvd.title";
	public static final String DVD_CHAPTER = "start.with.dvd.chapter";
	public static final String FILE_TO_PLAY = "file.to.play";
	
	private Properties prop;

	public AppConfig() {
		try {
			prop = new Properties();
			File firstChoiceLocation = new File(APP_PROPERTIES_FILENAME);
			InputStream input = null;
			if (firstChoiceLocation.exists()) {
				input = new FileInputStream(APP_PROPERTIES_FILENAME);
			} else {
				log.warn("config file {} not found, defaulting to embedded one...", APP_PROPERTIES_FILENAME);
				input = this.getClass().getResourceAsStream("/" + APP_PROPERTIES_FILENAME);
			}
			try (InputStream toCloseInput = input) {
				prop.load(toCloseInput);
			}
		} catch (Exception e) {
			throw new RuntimeException("Config file " + APP_PROPERTIES_FILENAME + " not found");
		}
	}

	public String get(String key) {
		return prop.getProperty(key);
	}
}
