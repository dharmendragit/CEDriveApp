package com.ce.entity;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
/**
 * 
 * @author DKP
 *
 */
public class APIResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String status;
	private String errorCode;
	private String errorMessage;
	
	
	public APIResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public APIResponse(String status, String errorCode, String errorMessage) {
		super();
		this.status = status;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
