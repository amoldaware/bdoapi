package com.cgtmse.dao;

public class JsonDataForToken {


	private Data    Data;
	private Integer Status;
	private String  ErrorDetails;
	private String  InfoDtls;
	
	

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getErrorDetails() {
		return ErrorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		ErrorDetails = errorDetails;
	}

	public String getInfoDtls() {
		return InfoDtls;
	}

	public void setInfoDtls(String infoDtls) {
		InfoDtls = infoDtls;
	}

}
