package com.cgtmse.dao;

import java.util.ArrayList;

public class IrnFailResponce {

	private int Status;
	ArrayList < Object > ErrorDetails = new ArrayList < Object > ();
	private String Data = null;
	private String InfoDtls = null;

	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public ArrayList<Object> getErrorDetails() {
		return ErrorDetails;
	}
	public void setErrorDetails(ArrayList<Object> errorDetails) {
		ErrorDetails = errorDetails;
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

}
