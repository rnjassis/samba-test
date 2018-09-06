package com.test.samba.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.test.samba.service.FileManagementS3;
import com.test.samba.util.S3IO;

/**
 * Class responsible for showing the result file
 * 
 * @author silva
 *
 */
@ManagedBean
@ViewScoped
public class WatchBean {

	public WatchBean() {

	}

	/**
	 * Returns the stream of the resulting file
	 * 
	 * @return
	 * @throws IOException
	 */
	public StreamedContent getStream() throws IOException {
		String fileName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("video");

		FileManagementS3 FMS3 = new FileManagementS3();
		return FMS3.getFile(fileName);
	}

	/**
	 * Returns the page
	 * 
	 * @return
	 */
	public String back() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("video", null);
		return "index.xhtml";
	}

}
