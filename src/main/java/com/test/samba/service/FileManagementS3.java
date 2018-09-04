package com.test.samba.service;

import java.util.UUID;

import org.primefaces.model.UploadedFile;

import com.test.samba.util.S3IO;
/**
 * Handles the files on S3, wich are located in a mounted folder in EC2
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
}
