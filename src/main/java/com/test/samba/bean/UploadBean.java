package com.test.samba.bean;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.test.samba.exception.FileManagementS3Exception;
import com.test.samba.exception.ZencoderEncodingException;
import com.test.samba.service.FileManagementS3;
import com.test.samba.service.ZencoderEncoding;
import com.test.samba.util.CheckForConvertedFile;

/**
 * Bean that handles the index request
 * 
 * @author silva
 *
 */
@ManagedBean
@ViewScoped
public class UploadBean {

	public boolean buttonVisible;

	/**
	 * Constructor (Does nothing)
	 */
	public UploadBean() {
		buttonVisible = false;
	}

	/**
	 * Post-Constructor (Does nothing)
	 */
	@PostConstruct
	private void postConstruct() {
		
	}

	/**
	 * Send the uploaded file to S3 and makes a request to the encoding service
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();

		FileManagementS3 fMs3 = new FileManagementS3();
		ZencoderEncoding zEnc = new ZencoderEncoding();

		String fileSaved;
		buttonVisible = false;
		RequestContext.getCurrentInstance().update("mainForm:btnRedirect");

		try {
			fileSaved = fMs3.SaveFile(file);
			zEnc.requestEncoding(fileSaved);

			System.out.println("File Saved: " + fileSaved);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("video", fileSaved.substring(0, fileSaved.lastIndexOf(".")) + ".mp4");
			
			RequestContext.getCurrentInstance().execute("PF('dlg1').show();");

		} catch (FileManagementS3Exception e) {
			hideModalStopPoll();
			showMessage(FacesMessage.SEVERITY_ERROR, "");
			e.printStackTrace();
		} catch (ZencoderEncodingException e) {
			hideModalStopPoll();
			showMessage(FacesMessage.SEVERITY_ERROR, "");
			e.printStackTrace();
		} catch (Exception e) {
			hideModalStopPoll();
			showMessage(FacesMessage.SEVERITY_ERROR, "");
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the encoded file is already in the filesystem
	 */
	public void checkForConverted() {
		Boolean result = false;
		try {
			ExecutorService executor = Executors.newCachedThreadPool();
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("video"));
			Future<Boolean> futureCall = executor.submit(new CheckForConvertedFile(
					(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("video")));
			result = futureCall.get();
			executor.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		
		if (result) {
			buttonVisible = true;
			showMessage(FacesMessage.SEVERITY_INFO, "Arquivo convertido");
			RequestContext.getCurrentInstance().update("mainForm:growl");

			RequestContext.getCurrentInstance().update("mainForm:panelUpload");

			hideModalStopPoll();
		}
	}

	/**
	 * Get the current visibility of the button
	 * @return : current visibility of the button
	 */
	public boolean isButtonVisible() {
		return buttonVisible;
	}

	/**
	 * Set the current visibility of the button
	 * @param buttonVisible : new visibility of the button
	 */
	public void setButtonVisible(boolean buttonVisible) {
		this.buttonVisible = buttonVisible;
	}

	/**
	 * Shows a message with a severity icon
	 * @param severity : severity icon
	 * @param message : message to be shown
	 */
	private void showMessage(Severity severity, String message) {
		FacesMessage facesMessage = new FacesMessage(severity, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	/**
	 * Stop looking for the file and hide the modal dialog
	 */
	private void hideModalStopPoll() {
		RequestContext.getCurrentInstance().execute("PF('dlg1').hide();");
		RequestContext.getCurrentInstance().execute("PF('poll').stop();");
	}
}
