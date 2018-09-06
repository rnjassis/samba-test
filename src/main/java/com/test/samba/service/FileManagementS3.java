package com.test.samba.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.test.samba.util.S3IO;

/**
 * Handles the files on S3, wich are located in a mounted folder in EC2
 * 
 * @author silva
 *
 */
public class FileManagementS3 {
	public FileManagementS3() {

	}

	public String SaveFile(UploadedFile uFile) throws Exception {
		String originalFileName = uFile.getFileName();
		String generatedUUID = UUID.randomUUID().toString().replaceAll("-", "");
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		String finalName = generatedUUID + extension;

		uFile.write(S3IO.getInputFilePath() + "/" + finalName);

		return finalName;

	}

	public DefaultStreamedContent getFile(String fileName) throws FileNotFoundException {
		File file = new File(S3IO.getOutputFilePath() + "/" + fileName);
		InputStream is = new FileInputStream(file);

		return new DefaultStreamedContent(is, "video/mp4", file.getName());
	}
}
