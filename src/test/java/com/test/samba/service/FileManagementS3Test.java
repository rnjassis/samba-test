package com.test.samba.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.test.samba.util.S3IO;

public class FileManagementS3Test {

	@Before
	public void setUp() throws Exception {
		File propertiesFile = new File("/home/ubuntu/config.properties");

		S3IO s3io = new S3IO(propertiesFile);
	}

	@Test
	public void testSaveFileNormal() {
		try {
			File file = new File("/home/ubuntu/testfile.mp4");
			FileInputStream inputStream;
			inputStream = new FileInputStream(file);
			FileManagementS3 fms3 = new FileManagementS3();
			FileUploadEvent fue = Mockito.mock(FileUploadEvent.class);
			UploadedFile uploadedFile = Mockito.mock(UploadedFile.class);

			Mockito.when(uploadedFile.getFileName()).thenReturn(file.getName());
			Mockito.when(uploadedFile.getContentType()).thenReturn("application/octet-stream");
			Mockito.when(uploadedFile.getInputstream()).thenReturn(inputStream);
			Mockito.when(fue.getFile()).thenReturn(uploadedFile);

			String filename = fms3.SaveFile(uploadedFile);
			assertNotNull(filename);
			assertNotEquals("", filename);

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testSaveFileFileNotFound() throws Exception {
		File file = new File("/home/ubuntu/anynamethatgetsanerror.mp4");
		FileInputStream inputStream;
		inputStream = new FileInputStream(file);
		FileManagementS3 fms3 = new FileManagementS3();
		FileUploadEvent fue = Mockito.mock(FileUploadEvent.class);
		UploadedFile uploadedFile = Mockito.mock(UploadedFile.class);

		Mockito.when(uploadedFile.getFileName()).thenReturn(file.getName());
		Mockito.when(uploadedFile.getContentType()).thenReturn("application/octet-stream");
		Mockito.when(uploadedFile.getInputstream()).thenReturn(inputStream);
		Mockito.when(fue.getFile()).thenReturn(uploadedFile);

		String filename = fms3.SaveFile(uploadedFile);

	}

	@Test
	public void testGetFileNormal() {
		FileManagementS3 fms3 = new FileManagementS3();
		try {
			assertNotNull(fms3.getFile("testfile.mp4"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testGetFileFileNotFound() throws FileNotFoundException {
		FileManagementS3 fms3 = new FileManagementS3();
			assertNotNull(fms3.getFile("anynamethatgetsanerror.mp4"));

	}
}
