package com.test.samba.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class S3IO {
	private static String inputFilePath;
	private static String outputFilePath;
	private Properties properties;

	public S3IO(File file) {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			
			properties = new Properties();
			properties.load(input);
			
			inputFilePath = properties.getProperty("input");
			outputFilePath = properties.getProperty("output");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getInputFilePath() {
		return inputFilePath;
	} 

	public static String getOutputFilePath() {
		return outputFilePath;
	}

}
