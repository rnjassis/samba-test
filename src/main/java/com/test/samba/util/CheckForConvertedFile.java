package com.test.samba.util;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Class that checks if a video is in the encoded folder
 * 
 * @author silva
 *
 */
public class CheckForConvertedFile implements Callable<Boolean> {
	private String fileSaved;

	public CheckForConvertedFile() {

	}

	/**
	 * Sets the file to be discovered
	 * 
	 * @param fileSaved
	 */
	public CheckForConvertedFile(String fileSaved) {
		this.fileSaved = fileSaved;
	}

	/**
	 * Starts the thread
	 */
	@Override
	public Boolean call() throws Exception {
		if (fileSaved != null) {
			fileSaved = fileSaved.substring(0, fileSaved.lastIndexOf(".")) + ".mp4";
			if (checkForNewFile(S3IO.getOutputFilePath() + "/" + fileSaved)) {
				System.out.println("found");
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches the file
	 * 
	 * @param fileSaved : name of the file
	 * @return : true if found the file; false otherwise
	 */
	private boolean checkForNewFile(String fileSaved) {
		File file = new File(fileSaved);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}

	public void setFileSaved(String fileSaved) {
		this.fileSaved = fileSaved;
	}

}
