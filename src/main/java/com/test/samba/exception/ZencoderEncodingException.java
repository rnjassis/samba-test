package com.test.samba.exception;
/**
 * Exception class for ZencoderEncoding class
 * @author silva
 *
 */
public class ZencoderEncodingException extends Exception{
	public ZencoderEncodingException() {
		
	}
	
	public ZencoderEncodingException(String message) {
		super(message);
	}
	
	public ZencoderEncodingException(String message, Throwable e) {
		super(message, e);
	}
}
