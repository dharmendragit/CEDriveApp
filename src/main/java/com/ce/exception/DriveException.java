package com.ce.exception;

public class DriveException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String errorCode;
	private String errorMessage;
	public DriveException(String status, String errorCode, String errorMessage) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public DriveException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	public DriveException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
