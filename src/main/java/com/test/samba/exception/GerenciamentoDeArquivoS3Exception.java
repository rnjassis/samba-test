package com.test.samba.exception;

public class GerenciamentoDeArquivoS3Exception extends Exception {
	public GerenciamentoDeArquivoS3Exception() {
		
	}
	
	public GerenciamentoDeArquivoS3Exception(String message) {
		super(message);
	}
	
	public GerenciamentoDeArquivoS3Exception(String message, Throwable cause) {
		super(message, cause);
	}
}
