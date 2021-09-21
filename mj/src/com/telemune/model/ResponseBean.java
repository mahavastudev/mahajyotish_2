package com.telemune.model;

public class ResponseBean {

	private String fileName;
	private String message;

	public void setFileName(String file) {
		fileName = file;
	}

	public void setMessage(String msg) {
		message = msg;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMessage() {
		return message;
	}

}
