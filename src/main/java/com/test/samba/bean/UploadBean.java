package com.test.samba.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UploadBean {
	
	public UploadBean() {
		// TODO Auto-generated constructor stub
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		
		System.out.println("handled");
	}
}
