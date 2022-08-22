package com.cgtmse.dao;

public class ErrorDetails {

	private String ErrorCode;

	private String ErrorMessage;

	public void setErrorCode(String ErrorCode){
		this.ErrorCode = ErrorCode;
	}
	public String getErrorCode(){
		return this.ErrorCode;
	}
	public void setErrorMessage(String ErrorMessage){
		this.ErrorMessage = ErrorMessage;
	}
	public String getErrorMessage(){
		return this.ErrorMessage;
	}
}
