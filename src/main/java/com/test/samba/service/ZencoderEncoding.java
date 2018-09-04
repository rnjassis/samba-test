package com.test.samba.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.test.samba.exception.ZencoderEncodingException;
import com.test.samba.util.S3IO;
import com.test.samba.util.ZencoderConfig;

/**
 * Creates Zencoder jobs
 * 
 * @author silva
 *
 */
public class ZencoderEncoding {

	public ZencoderEncoding() {

	}

	/**
	 * Create the Zencoder request to encode the uploaded video
	 * 
	 * @param S3Location : path to the video stored in S3
	 * @throws ZencoderEncodingException
	 */
	public JSONObject requestEncoding(String S3Location) throws ZencoderEncodingException {
		URL url;
		HttpURLConnection httpConnection = null;

		try {
			// Creating the request
			url = new URL("https://app.zencoder.com/api/v2/jobs");

			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");

			// Setting request headers
			httpConnection.setRequestProperty("Zencoder-Api-Key", ZencoderConfig.getApiKey());
			httpConnection.setRequestProperty("Content-Type", "application/json");

			// Making the request
			DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
			wr.write(generateParameters(S3Location).toString().getBytes("UTF-8"));
			wr.flush();

			// Getting response
			StringBuilder sb = new StringBuilder();
			int HttpResult = httpConnection.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_CREATED) {
				BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return new JSONObject(sb.toString());
			} else {
				System.out.println(httpConnection.getResponseMessage());
				return new JSONObject("{\"error\":\"" + httpConnection.getResponseMessage() + "\"}");
			}

		} catch (MalformedURLException e) {
			throw new ZencoderEncodingException("MalformedURLException", e);
		} catch (IOException e) {
			throw new ZencoderEncodingException("IOException", e);
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
	}

	/**
	 * Generate parameters for request
	 * 
	 * @param fileName : File name in S3
	 * @return JSON with the parameters
	 */
	private JSONObject generateParameters(String fileName) {
		JSONObject result = new JSONObject();
		JSONObject outputFormat = new JSONObject();
		JSONArray outputs = new JSONArray();

		result.put("input", S3IO.getS3InputFilePath() + "/" + fileName);

		outputFormat.put("url",
				S3IO.getS3OutputFilePath() + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4");
		outputFormat.put("label", "mp4 high");
		outputFormat.put("h264_profile", "high");
		outputs.put(outputFormat);

		result.put("outputs", outputs);
		System.out.println(result);
		return result;
	}
}
