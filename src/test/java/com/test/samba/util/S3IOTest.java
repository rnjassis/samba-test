package com.test.samba.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class S3IOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testS3IONormal() {
		File propertiesFile = new File("/home/ubuntu/config.properties");
		S3IO s3io = new S3IO(propertiesFile);
		
		assertNotNull(S3IO.getInputFilePath());
		assertNotNull(S3IO.getOutputFilePath());
		assertNotNull(S3IO.getS3InputFilePath());
		assertNotNull(S3IO.getS3OutputFilePath());
	}
	
	@Test
	public void testS3IOFileNotFound() {
		File propertiesFile = new File("/home/ubuntu/anynamethatgetsanerror.mp4");
		S3IO s3io = new S3IO(propertiesFile);
		
		assertNull(S3IO.getInputFilePath());
		assertNull(S3IO.getOutputFilePath());
		assertNull(S3IO.getS3InputFilePath());
		assertNull(S3IO.getS3OutputFilePath());
	}

}
