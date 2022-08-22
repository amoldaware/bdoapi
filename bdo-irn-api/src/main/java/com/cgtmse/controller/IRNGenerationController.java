package com.cgtmse.controller;

/*
 * 
 * Author:Amol Daware
 * 
 * 
 * */
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgtmse.bdo.RSAEncryptionUtility;
import com.cgtmse.dao.JavaCodeToJson;
import com.cgtmse.dao.JsonResponceError;
import com.cgtmse.dao.JsonResponceSuccess;
import com.cgtmse.modal.IrnData;
import com.cgtmse.modal.IrnDataImpl;
//import com.cgtmse.repository.BdoAuthTokenRepository;
import com.cgtmse.utility.IrnUtility;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/cgt")
public class IRNGenerationController {
//	@Autowired
//	private BdoAuthTokenRepository bdoAuthTokenRepository;
	static Logger log = Logger.getLogger(IRNGenerationController.class.getName());

	@Value("${bdo.public.key.path}")
	private String bdoPubKeyPath;
	@Value("${nic.public.key.path}")
	private String nicPubKeyPath;

	@Value("${cgtmse.username}")
	private String userName;
	
	@Value("${cgtmse.password}")
	private String password;
	
	String encryptedAppKey = null, encryptedClientSecret = null, decryptedBDoSek = null;
	String decryptedResponse = null, bdo_authtoken = null, bdo_sek = null;

	
//	String client_id = "ad3130832048cab8051412474b0681"; ///UAT 
//	final String client_secret = "13fec05d14ae83308e01cdd2370c70"; ///UAT

	String client_id = "a9e95119bac180edbb1cfe440f9be9"; ///Prod
	final String client_secret="470e182869089ac932f15e79c38040"; ///Prod

	@PostMapping(value = "/generateirn")
	public String generateIRN(@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate)
			throws Exception, JSONException {

		// -----Generate Random 32 bit App Key
		final String appKey = RandomStringUtils.randomAlphanumeric(32);
		System.out.println("New AppKey : " + appKey + " on :" + LocalDateTime.now());

		// ------Encrypt the generated random App Key
		try {
			final PublicKey publicKey = RSAEncryptionUtility.getPublicKey(this.bdoPubKeyPath);
			encryptedAppKey = new RSAEncryptionUtility().encryptTextWithPublicKey(appKey.trim(), publicKey);
//	            return encText;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		System.out.println("encryptedAppKey:" + encryptedAppKey);
		// -----Encrypt the Client secret key

		try {
			final PublicKey publicKey = RSAEncryptionUtility.getPublicKey(this.bdoPubKeyPath);
			encryptedClientSecret = new RSAEncryptionUtility().encryptTextWithPublicKey(client_secret.trim(),
					publicKey);
//	            return encText;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		System.out.println("encryptedClientSecret:" + encryptedClientSecret);
		boolean chkExpiryDate = IrnDataImpl.chkExpiry();
	     if(chkExpiryDate==false) {
		System.out.println("check expiry false");
		// }else {
		// System.out.println("false");
		// }

		String jsonInputString = "{\"clientid\":\"" + client_id + "\",\"clientsecretencrypted\":\""
				+ encryptedClientSecret + "\",\"appsecretkey\":\"" + encryptedAppKey +"\",\"username\":\"" + this.userName +"\",\"password\":\"" + this.password + "\"}";
//		 IrnData irnData=new IrnData();
//		 irnData.setClientid(client_id);
//		 irnData.setClientsecretencrypted(encryptedClientSecret);
//		 irnData.setAppsecretkey(encryptedAppKey);
//		 irnData.setAppkey(appKey);
//		 bdoAuthTokenRepository.save(irnData);

//	        String saveData=
//	        jsonInputString = Base64.encodeBase64String(jsonInputString.getBytes());

//	        System.out.println("encryptedClientSecret:" + encryptedClientSecret);
		Gson gson = new Gson();

		// BDO authentication response
		String bdoResponceObj = IrnUtility.call_E_InvoiceAuthAPI(jsonInputString);
		System.out.println("bdoResponceObj:::::::" + bdoResponceObj);
		// Map<String, String>
		// mapMode=IrnUtility.call_E_InvoiceAuthAPI(jsonInputString);
		JSONObject obj = new JSONObject(bdoResponceObj);

//	        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
		String expiry = obj.getString("expiry");
		bdo_authtoken = obj.getString("bdo_authtoken");
		bdo_sek = obj.getString("bdo_sek");
//	        irnData.setBdo_authtoken(bdo_authtoken);
//	        irnData.setExpiry(expiry);
//	        irnData.setBdo_sek(bdo_sek);
//	        bdoAuthTokenRepository.save(irnData);
		System.out.println("11111111");
		Map<String, String> map = new HashMap<String, String>();
		map.put("appkey", appKey);
		map.put("sek", bdo_sek);
		decryptedBDoSek = IrnUtility.decryptSEK(map);
		
		boolean irndataSave = IrnDataImpl.saveData(client_id, encryptedClientSecret, encryptedAppKey, bdo_authtoken,
				expiry, bdo_sek, appKey, jsonInputString, bdoResponceObj,decryptedBDoSek);
		
		if (irndataSave == true) {
			// Decrypt sek
		System.out.println("Auth data saved");	

		} else {
			System.out.println("Failed to save auth data");
		}
		System.out.println("2222222222");
//	     }else {

		Map<String, String> chkLastAuthData = IrnDataImpl.chkLastAuth();

		System.out.println("decryptedBDOSEK:::::"+chkLastAuthData.get(""));
//	        irnDataimpl
//	     String invoiceIdList = new ArrayList<String>();
		ArrayList<String> invoiceIdList = IrnDataImpl.getInvoiceIds("CGT", fromdate, todate);
		System.out.println("invoiceIdList.size()::::::::" + invoiceIdList.size());

		for (int i = 0; i < invoiceIdList.size(); i++) {

			System.out.println("invoiceIdList::::::::::" + i);
			String Request_json = JavaCodeToJson.convertDataToJson(invoiceIdList.get(i));// checking connection and
																							// closing connection
//					
			System.out.println("Request_json:::::::" + Request_json);
			JSONParser jsonParser = new JSONParser();
			JSONObject jobj = new JSONObject(Request_json);
			// if(!itemArray.isEmpty() ) {

//					String encryptPayload=IrnUtility.encryptPayload(invoiceIdList.get(i),chkLastAuthData.get("decryptedBDoSek"));
			String encryptPayload = IrnUtility.encryptPayload(Request_json, decryptedBDoSek);
//	        String encryptPayload=IrnUtility.encryptPayload(IrnUtility.payload1,decryptedBDoSek);
			System.out.println("encryptPayload:" + encryptPayload);
			encryptPayload = "{\"Data\":\"" + encryptPayload + "\"}";
			System.out.println("encryptPayload:::" + encryptPayload);
			// Call generate Irn URL
			try {
				String bdoIrnResponceObj = IrnUtility.callGenerateIrn(encryptPayload, bdo_authtoken, client_id);
//	        String bdoIrnResponceObj =  IrnUtility.callGenerateIrn(encryptPayload,chkLastAuthData.get("bdo_authtoken"),chkLastAuthData.get("client_id"));

				System.out.println("bdoIrnResponceObj:" + bdoIrnResponceObj);
				// Decrypt IRN Response

				JSONObject objResponse = new JSONObject(bdoIrnResponceObj);
				
				int statusFailRepsonse = 0, statusSuccessRepsonse = 0;
				if (objResponse.has("status")) {
					statusFailRepsonse = Integer.parseInt(objResponse.get("status").toString());
					System.out.println("statusFailRepsonse::::" + statusFailRepsonse);
				} else if (objResponse.has("Status")) {
					statusSuccessRepsonse = Integer.parseInt(objResponse.get("Status").toString());
					System.out.println("statusSuccessRepsonse::::" + statusSuccessRepsonse);
				}
				java.util.Date sDate = (java.util.Date) new Date();
				java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());
				boolean responseStatus = false;
				
				
				if (statusSuccessRepsonse == 1) {
//	        Gson gson = new Gson();       
					decryptedResponse = IrnUtility.decryptResponse(objResponse.getString("Data"), decryptedBDoSek);
//	        decryptedResponse=IrnUtility.decryptResponse(objResponse.getString("Data"),chkLastAuthData.get("decryptedBDoSek"));

//					JsonResponceError getResponcObj = gson.fromJson(decryptedResponse.toString(),
//							JsonResponceError.class);

					System.out.println("Finally Api IRN Responce Status::::::::::::" + decryptedResponse+ ":::::::::::::::::::::::::");
					String decrypted_response = "";
					JsonResponceSuccess successResponcObj = null;
//					System.out.println("getResponcObj::::::" + getResponcObj);

//			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");								
					// System.out.println("API Final status==="+getResponcObj.getStatus());
//			log.info("::::::IRN Responce Status::::"+getResponcObj.irnStatus()+" ::::::::::::");

					responseStatus = IrnDataImpl.updateResponse(Request_json,decryptedResponse, sqlDate, invoiceIdList.get(i),bdo_authtoken,decryptedBDoSek);
					if (responseStatus == true) {
						System.out.println("Success record saved:");
					} else {
						System.out.println("Fail to save success records");
					}

				} else if (statusFailRepsonse == 0) {

					responseStatus = IrnDataImpl.updateErrorResponse(bdoIrnResponceObj, sqlDate, invoiceIdList.get(i));
					if (responseStatus == true) {
						System.out.println("failed record saved:");
					} else {
						System.out.println("Fail to save fail records");
					}

				}

				System.out.println("decryptedResponse:" + decryptedResponse);
			} catch (Exception e) {
				System.out.println("Exception in after expiry of auth token payload::::::::::::"+e.getMessage());
			}
			System.out.println("decryptedResponse::::::::::" + i);
		}
//	     }
//			 return decryptedResponse;		

	}else {
		Map<String, String> chkLastAuthData = IrnDataImpl.chkLastAuth();
		System.out.println("check expiry true");
		
		JSONObject jobChkLastAuthData = new JSONObject(chkLastAuthData);
		
		decryptedBDoSek=jobChkLastAuthData.getString("decryptedBDoSek");
		bdo_authtoken=jobChkLastAuthData.getString("bdo_authtoken");
		client_id=jobChkLastAuthData.getString("client_id");
		System.out.println("decryptedBDoSek:::"+decryptedBDoSek+"  ");
		System.out.println("bdo_authtoken:::"+bdo_authtoken+"  ");
		System.out.println("client_id:::"+client_id+"  ");
//        irnDataimpl
//     String invoiceIdList = new ArrayList<String>();
	ArrayList<String> invoiceIdList = IrnDataImpl.getInvoiceIds("CGT", fromdate, todate);
	System.out.println("invoiceIdList.size()::::::::" + invoiceIdList.size());

	for (int i = 0; i < invoiceIdList.size(); i++) {

		System.out.println("invoiceIdList::::::::::" + i);
		String Request_json = JavaCodeToJson.convertDataToJson(invoiceIdList.get(i));// checking connection and
																						// closing connection
//				
		System.out.println("Request_json:::::::" + Request_json);
		JSONParser jsonParser = new JSONParser();
		JSONObject jobj = new JSONObject(Request_json);
		 if(!invoiceIdList.get(i).isEmpty() ) {

//				String encryptPayload=IrnUtility.encryptPayload(invoiceIdList.get(i),chkLastAuthData.get("decryptedBDoSek"));
//		String encryptPayload = IrnUtility.encryptPayload(Request_json, chkLastAuthData.get("decryptedBDoSek"));
			 String encryptPayload = IrnUtility.encryptPayload(Request_json, decryptedBDoSek);
//        String encryptPayload=IrnUtility.encryptPayload(IrnUtility.payload1,decryptedBDoSek);
		System.out.println("encryptPayload:" + encryptPayload);
		encryptPayload = "{\"Data\":\"" + encryptPayload + "\"}";
		System.out.println("encryptPayload:::" + encryptPayload);
		// Call generate Irn URL
		try {
			String bdoIrnResponceObj = IrnUtility.callGenerateIrn(encryptPayload, bdo_authtoken, client_id);
//        String bdoIrnResponceObj =  IrnUtility.callGenerateIrn(encryptPayload,chkLastAuthData.get("bdo_authtoken"),chkLastAuthData.get("client_id"));

			System.out.println("bdoIrnResponceObj:" + bdoIrnResponceObj);
			// Decrypt IRN Response

			JSONObject objResponse = new JSONObject(bdoIrnResponceObj);
			
			int statusFailRepsonse = 0, statusSuccessRepsonse = 0;
			if (objResponse.has("status")) {
				statusFailRepsonse = Integer.parseInt(objResponse.get("status").toString());
				System.out.println("statusFailRepsonse::::" + statusFailRepsonse);
			} else if (objResponse.has("Status")) {
				statusSuccessRepsonse = Integer.parseInt(objResponse.get("Status").toString());
				System.out.println("statusSuccessRepsonse::::" + statusSuccessRepsonse);
			}
			java.util.Date sDate = (java.util.Date) new Date();
			java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());
			boolean responseStatus = false;
			
			
			if (statusSuccessRepsonse == 1) {
//        Gson gson = new Gson();       
				decryptedResponse = IrnUtility.decryptResponse(objResponse.getString("Data"), decryptedBDoSek);
//        decryptedResponse=IrnUtility.decryptResponse(objResponse.getString("Data"),chkLastAuthData.get("decryptedBDoSek"));

//				JsonResponceError getResponcObj = gson.fromJson(decryptedResponse.toString(),
//						JsonResponceError.class);

				System.out.println("Finally Api IRN Responce Status::::::::::::" + decryptedResponse+ ":::::::::::::::::::::::::");
				String decrypted_response = "";
				JsonResponceSuccess successResponcObj = null;
//				System.out.println("getResponcObj::::::" + getResponcObj);

//		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");								
				// System.out.println("API Final status==="+getResponcObj.getStatus());
//		log.info("::::::IRN Responce Status::::"+getResponcObj.irnStatus()+" ::::::::::::");

				responseStatus = IrnDataImpl.updateResponse(Request_json,decryptedResponse, sqlDate, invoiceIdList.get(i),bdo_authtoken,decryptedBDoSek);
				if (responseStatus == true) {
					System.out.println("Success record saved:");
				} else {
					System.out.println("Fail to save success records");
				}

			} else if (statusFailRepsonse == 0) {

				responseStatus = IrnDataImpl.updateErrorResponse(bdoIrnResponceObj, sqlDate, invoiceIdList.get(i));
				if (responseStatus == true) {
					System.out.println("failed record saved:");
				} else {
					System.out.println("Fail to save fail records");
				}

			}

			System.out.println("decryptedResponse:" + decryptedResponse);
		} catch (Exception e) {
			System.out.println("Exception in before auth token expire payload::::::::::::"+e.getMessage());
		}
		System.out.println("decryptedResponse::::::::::" + i);
		 }else {
				System.out.println(":::::::::No invoice pending for IRN generation::::::");
					 
		 }
	}
	}
		return decryptedResponse;
//		return appKey;
	}

	
	@PostMapping(value = "/cancelirn")
	public String cancelIRN(@RequestParam("invoice_no") List<String> invoiceList,@RequestParam("remark") String remark,@RequestParam("reason") String reason)
			throws Exception, JSONException {
		String cancelInvoiceNo =null;
	    if(invoiceList != null){
	        for(String invoice_no : invoiceList){
	        	System.out.println("invoice_no::"+invoice_no);
	        	cancelInvoiceNo = IrnDataImpl.geCanceltInvoiceId(invoice_no,reason,remark);
	        }
	        System.out.println("cancelInvoiceNo:::::::"+cancelInvoiceNo);
	    }
		
//		String cancelInvoiceNo = IrnDataImpl.geCanceltInvoiceId(invoice_no,reason,remark);
				return cancelInvoiceNo;
	}

	@GetMapping(value = "/getirn")
	public String getIRN(@RequestParam("doctype") String doctype,@RequestParam("docnum") String docnum,@RequestParam("docdate") String docdate)
			throws Exception, JSONException {
		String getInvoiceNo =null;
//	    if(invoiceList != null){
//	        for(String invoice_no : invoiceList){
	        	System.out.println("invoice_no::"+docnum);
		final String appKey = RandomStringUtils.randomAlphanumeric(32);
		System.out.println("New AppKey : " + appKey + " on :" + LocalDateTime.now());

		// ------Encrypt the generated random App Key
		try {
			final PublicKey publicKey = RSAEncryptionUtility.getPublicKey(this.bdoPubKeyPath);
			encryptedAppKey = new RSAEncryptionUtility().encryptTextWithPublicKey(appKey.trim(), publicKey);
//	            return encText;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		System.out.println("encryptedAppKey:" + encryptedAppKey);
		// -----Encrypt the Client secret key

		try {
			final PublicKey publicKey = RSAEncryptionUtility.getPublicKey(this.bdoPubKeyPath);
			encryptedClientSecret = new RSAEncryptionUtility().encryptTextWithPublicKey(client_secret.trim(),
					publicKey);
//	            return encText;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		System.out.println("encryptedClientSecret:" + encryptedClientSecret);
		boolean chkExpiryDate = IrnDataImpl.chkExpiry();
	     if(chkExpiryDate==false) {
		System.out.println("check expiry false");
		// }else {
		// System.out.println("false");
		// }

		String jsonInputString = "{\"clientid\":\"" + client_id + "\",\"clientsecretencrypted\":\""
				+ encryptedClientSecret + "\",\"appsecretkey\":\"" + encryptedAppKey + "\"}";
		
		Gson gson = new Gson();

		// BDO authentication response
		String bdoResponceObj = IrnUtility.call_E_InvoiceAuthAPI(jsonInputString);
		System.out.println("bdoResponceObj:::::::" + bdoResponceObj);
		// Map<String, String>
		// mapMode=IrnUtility.call_E_InvoiceAuthAPI(jsonInputString);
		JSONObject obj = new JSONObject(bdoResponceObj);

//	        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
		String expiry = obj.getString("expiry");
		bdo_authtoken = obj.getString("bdo_authtoken");
		bdo_sek = obj.getString("bdo_sek");
//	        irnData.setBdo_authtoken(bdo_authtoken);
//	        irnData.setExpiry(expiry);
//	        irnData.setBdo_sek(bdo_sek);
//	        bdoAuthTokenRepository.save(irnData);
		System.out.println("11111111");
		Map<String, String> map = new HashMap<String, String>();
		map.put("appkey", appKey);
		map.put("sek", bdo_sek);
		decryptedBDoSek = IrnUtility.decryptSEK(map);
		
		boolean irndataSave = IrnDataImpl.saveData(client_id, encryptedClientSecret, encryptedAppKey, bdo_authtoken,
				expiry, bdo_sek, appKey, jsonInputString, bdoResponceObj,decryptedBDoSek);
		
		if (irndataSave == true) {
			// Decrypt sek
		System.out.println("Auth data saved");	

		} else {
			System.out.println("Failed to save auth data");
		}
		System.out.println("2222222222");
//	     }else {

			// Call generate Irn URL
			try {
				String bdoIrnResponceObj = IrnUtility.getGenerateIrn(docnum, docdate,bdo_authtoken, client_id);
//	        String bdoIrnResponceObj =  IrnUtility.callGenerateIrn(encryptPayload,chkLastAuthData.get("bdo_authtoken"),chkLastAuthData.get("client_id"));

				System.out.println("bdoIrnResponceObj:" + bdoIrnResponceObj);
				// Decrypt IRN Response

				JSONObject objResponse = new JSONObject(bdoIrnResponceObj);
				
				int statusFailRepsonse = 0, statusSuccessRepsonse = 0;
				if (objResponse.has("status")) {
					statusFailRepsonse = Integer.parseInt(objResponse.get("status").toString());
					System.out.println("statusFailRepsonse::::" + statusFailRepsonse);
				} else if (objResponse.has("Status")) {
					statusSuccessRepsonse = Integer.parseInt(objResponse.get("Status").toString());
					System.out.println("statusSuccessRepsonse::::" + statusSuccessRepsonse);
				}
				java.util.Date sDate = (java.util.Date) new Date();
				java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());
				boolean responseStatus = false;
				
				
				if (statusSuccessRepsonse == 1) {
//	        Gson gson = new Gson();       
					decryptedResponse = IrnUtility.decryptResponse(objResponse.getString("Data"), decryptedBDoSek);
//	        decryptedResponse=IrnUtility.decryptResponse(objResponse.getString("Data"),chkLastAuthData.get("decryptedBDoSek"));

//					JsonResponceError getResponcObj = gson.fromJson(decryptedResponse.toString(),
//							JsonResponceError.class);

					System.out.println("Finally Api IRN Responce Status::::::::::::" + decryptedResponse+ ":::::::::::::::::::::::::");
					String decrypted_response = "";
					JsonResponceSuccess successResponcObj = null;
//					System.out.println("getResponcObj::::::" + getResponcObj);

//			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");								
					// System.out.println("API Final status==="+getResponcObj.getStatus());
//			log.info("::::::IRN Responce Status::::"+getResponcObj.irnStatus()+" ::::::::::::");

					responseStatus = IrnDataImpl.updateGetIrnResponse(bdoIrnResponceObj,decryptedResponse, sqlDate, docnum,bdo_authtoken,decryptedBDoSek);
					if (responseStatus == true) {
						System.out.println("Success record saved:");
					} else {
						System.out.println("Fail to save success records");
					}

				} else if (statusFailRepsonse == 0) {

					responseStatus = IrnDataImpl.updateErrorResponse(bdoIrnResponceObj, sqlDate, docnum);
					if (responseStatus == true) {
						System.out.println("failed record saved:");
					} else {
						System.out.println("Fail to save fail records");
					}

				}

				System.out.println("decryptedResponse:" + decryptedResponse);
			} catch (Exception e) {
				System.out.println("Exception in after expiry of auth token payload::::::::::::"+e.getMessage());
			}
//			System.out.println("decryptedResponse::::::::::" + i);
		}
//	     }
//			 return decryptedResponse;		

	//}
	else {
		Map<String, String> chkLastAuthData = IrnDataImpl.chkLastAuth();
		System.out.println("check expiry true");
		
		JSONObject jobChkLastAuthData = new JSONObject(chkLastAuthData);
		
		decryptedBDoSek=jobChkLastAuthData.getString("decryptedBDoSek");
		bdo_authtoken=jobChkLastAuthData.getString("bdo_authtoken");
		client_id=jobChkLastAuthData.getString("client_id");
		System.out.println("decryptedBDoSek:::"+decryptedBDoSek+"  ");
		System.out.println("bdo_authtoken:::"+bdo_authtoken+"  ");
		System.out.println("client_id:::"+client_id+"  ");
		// Call generate Irn URL
		try {
			String bdoIrnResponceObj = IrnUtility.getGenerateIrn(docnum, docdate,bdo_authtoken, client_id);
//        String bdoIrnResponceObj =  IrnUtility.callGenerateIrn(encryptPayload,chkLastAuthData.get("bdo_authtoken"),chkLastAuthData.get("client_id"));

			System.out.println("bdoIrnResponceObj:" + bdoIrnResponceObj);
			// Decrypt IRN Response

			JSONObject objResponse = new JSONObject(bdoIrnResponceObj);
			
			int statusFailRepsonse = 0, statusSuccessRepsonse = 0;
			if (objResponse.has("status")) {
				statusFailRepsonse = Integer.parseInt(objResponse.get("status").toString());
				System.out.println("statusFailRepsonse::::" + statusFailRepsonse);
			} else if (objResponse.has("Status")) {
				statusSuccessRepsonse = Integer.parseInt(objResponse.get("Status").toString());
				System.out.println("statusSuccessRepsonse::::" + statusSuccessRepsonse);
			}
			java.util.Date sDate = (java.util.Date) new Date();
			java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());
			boolean responseStatus = false;
			
			
			if (statusSuccessRepsonse == 1) {
//        Gson gson = new Gson();       
				decryptedResponse = IrnUtility.decryptResponse(objResponse.getString("Data"), decryptedBDoSek);
				System.out.println("Finally Api IRN Responce Status::::::::::::" + decryptedResponse+ ":::::::::::::::::::::::::");
				String decrypted_response = "";
				JsonResponceSuccess successResponcObj = null;
				responseStatus = IrnDataImpl.updateGetIrnResponse(bdoIrnResponceObj,decryptedResponse, sqlDate,docnum ,bdo_authtoken,decryptedBDoSek);
				if (responseStatus == true) {
					System.out.println("Success record saved:");
				} else {
					System.out.println("Fail to save success records");
				}

			} else if (statusFailRepsonse == 0) {

				responseStatus = IrnDataImpl.updateErrorResponse(bdoIrnResponceObj, sqlDate, docnum);
				if (responseStatus == true) {
					System.out.println("failed record saved:");
				} else {
					System.out.println("Fail to save fail records");
				}

			}

			System.out.println("decryptedResponse:" + decryptedResponse);
		} catch (Exception e) {
			System.out.println("Exception in before auth token expire payload::::::::::::"+e.getMessage());
		}
//		System.out.println("decryptedResponse::::::::::" + i);
//		 }else {
//				System.out.println(":::::::::No invoice pending for IRN generation::::::");
//					 
//		 }
	}
//	}
		return decryptedResponse;
//		return appKey;
//	}


//		IrnDataImpl irnDataImpl=new IrnDataImpl();
//		getInvoiceNo = irnDataImpl.getInvoiceId(docnum,doctype,docdate);
//	        }
//	        System.out.println("cancelInvoiceNo:::::::"+cancelInvoiceNo);
//	    }
		
//		String cancelInvoiceNo = IrnDataImpl.geCanceltInvoiceId(invoice_no,reason,remark);
	//			return getInvoiceNo;
	}
}
