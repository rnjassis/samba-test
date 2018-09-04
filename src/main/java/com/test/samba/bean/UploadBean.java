package com.test.samba.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.test.samba.exception.FileManagementS3Exception;
import com.test.samba.exception.ZencoderEncodingException;
import com.test.samba.service.FileManagementS3;
import com.test.samba.service.ZencoderEncoding;

/**
 * Bean that handles the index request
 * @author silva
 *
 */
@ManagedBean
@SessionScoped
public class UploadBean {
	
	public UploadBean() {
		
	}

	/**
	 * Send the uploaded file to S3, request the encoding and update the view to show the video.
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		
		FileManagementS3 fMs3 = new FileManagementS3();
		ZencoderEncoding zEnc = new ZencoderEncoding();
		
		try {
			String fileSaved = fMs3.SaveFile(file);
			
			zEnc.requestEncoding(fileSaved);
			
			System.out.println("File Saved: "+fileSaved);
		} catch (FileManagementS3Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ZencoderEncodingException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
