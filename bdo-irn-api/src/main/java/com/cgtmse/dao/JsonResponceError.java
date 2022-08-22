package com.cgtmse.dao;

import java.util.ArrayList;

public class JsonResponceError {
	private FieldData fieldData;
	private Integer Status;
	private String Data;
	private ArrayList<ErrorDetails> ErrorDetails;
	private ArrayList<InfoDtls> InfoDtls;

	public FieldData getFieldData() {
		return fieldData;
	}

	public void setFieldData(FieldData fieldData) {
		this.fieldData = fieldData;
	}

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

	public ArrayList<ErrorDetails> getErrorDetails() {
		return ErrorDetails;
	}

	public void setErrorDetails(ArrayList<ErrorDetails> errorDetails) {
		ErrorDetails = errorDetails;
	}

	public ArrayList<InfoDtls> getInfoDtls() {
		return InfoDtls;
	}

	public void setInfoDtls(ArrayList<InfoDtls> infoDtls) {
		InfoDtls = infoDtls;
	}

	@Override
	public String toString() {
		return "JsonResponceError [Status=" + Status + ", Data=" + Data +", fieldData=" + fieldData +", ErrorDetails=" + ErrorDetails +", InfoDtls=" + InfoDtls+ "]";
	}
}
