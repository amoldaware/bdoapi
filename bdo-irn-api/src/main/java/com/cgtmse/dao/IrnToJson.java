package com.cgtmse.dao;

import com.google.gson.Gson;

public class IrnToJson {
	
	public static String convertIrnDataToJson(String selectIrnValue) {
		
		CancelIrn fieldData = new CancelIrn();		
		fieldData.setIrn(selectIrnValue);
		fieldData.setCnlRsn("1");
		fieldData.setCnlRem("cancel Remarks");	
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(fieldData);
		System.out.println(jsonString);

		return jsonString;
	}

}
