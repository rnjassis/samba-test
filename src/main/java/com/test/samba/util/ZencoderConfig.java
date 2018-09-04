package com.test.samba.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Zencoder configuration class
 * @author silva
 *
 */
public class ZencoderConfig {
	public static String apiKey;

	private Properties properties;

	public ZencoderConfig(File file) {
		InputStream input = null;
		try {
			input = new FileInputStream(file);

			properties = new Properties();
			properties.load(input);

			apiKey = properties.getProperty("apiKey");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
/**
 * Gets the ApiKey
 * @return The loaded ApiKey
 */
	public static String getApiKey() {
		return apiKey;
	}

}
