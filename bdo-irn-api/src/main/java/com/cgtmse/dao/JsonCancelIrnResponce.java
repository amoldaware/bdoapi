package com.cgtmse.dao;

import java.util.ArrayList;

public class JsonCancelIrnResponce {

	 private Integer Status;
	 private String Data;
	 private String InfoDtls;	
	 ArrayList < Object > ErrorDetails = new ArrayList < Object > ();	
	
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getInfoDtls() {
		return InfoDtls;
	}
	public void setInfoDtls(String infoDtls) {
		InfoDtls = infoDtls;
	}
	public void setErrorDetails(ArrayList<Object> errorDetails) {
		ErrorDetails = errorDetails;
	}
		 
}
