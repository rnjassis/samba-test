package com.test.samba.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.test.samba.exception.GerenciamentoDeArquivoS3Exception;
import com.test.samba.servico.GerenciamentoDeArquivoS3;

@ManagedBean
@SessionScoped
public class UploadBean {
	
	public UploadBean() {
		// TODO Auto-generated constructor stub
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		
		GerenciamentoDeArquivoS3 abc = new GerenciamentoDeArquivoS3();
		try {
			String fileSaved = abc.salvarArquivoS3(file);
			
			System.out.println("File Saved: "+fileSaved);
		} catch (GerenciamentoDeArquivoS3Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
