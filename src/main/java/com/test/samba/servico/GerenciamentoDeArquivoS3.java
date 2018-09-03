package com.test.samba.servico;

import java.util.UUID;

import org.primefaces.model.UploadedFile;

import com.test.samba.exception.GerenciamentoDeArquivoS3Exception;
import com.test.samba.util.S3IO;

public class GerenciamentoDeArquivoS3 {
	public GerenciamentoDeArquivoS3() {
		// TODO Auto-generated constructor stub
	}

	public String salvarArquivoS3(UploadedFile uFile) throws GerenciamentoDeArquivoS3Exception {
		try {
			String originalFileName = uFile.getFileName();
			String generatedUUID = UUID.randomUUID().toString().replaceAll("-", "");
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			String finalName = generatedUUID + extension;
			uFile.write(S3IO.getInputFilePath() + "/" + finalName);

			return finalName;
		} catch (Exception e) {
			throw new GerenciamentoDeArquivoS3Exception("Error writing the input file: ", e);
		}

	}
}
