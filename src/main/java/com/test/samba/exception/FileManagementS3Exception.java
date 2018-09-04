package com.test.samba.exception;
/**
 * Exception class for FileManagement class
 * @author silva
 *
 */
public class FileManagementS3Exception extends Exception {
	public FileManagementS3Exception() {
		
	}
	
	public FileManagementS3Exception(String message) {
		super(message);
	}
	
	public FileManagementS3Exception(String message, Throwable cause) {
		super(message, cause);
	}
}
