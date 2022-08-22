package com.cgtmse.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cgtmse.modal.IrnDataImpl;

public class IrnUtility {

	
	public static String call_E_InvoiceAuthAPI(String jsonInputString) throws Exception {
		
		 
					URL URL = null;
					try {
						
//						URL = new URL("https://sandboxeinvoiceapi.bdo.in/bdoauth/bdoauthenticate");
						URL = new URL("https://einvoiceapi.bdo.in/bdoauth/bdoauthenticate");
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						System.out.println("api hit url error:"+e1.getMessage());
						e1.printStackTrace();
					}
					HttpURLConnection con = (HttpURLConnection) URL.openConnection();
					try {
						con.setRequestMethod("POST");
					} catch (ProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					   		
				
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("Accept", "application/json");
//					con.setRequestProperty("clientid", client_id);
//					con.setRequestProperty("clientsecretencrypted", encryptedClientSecret);
//					con.setRequestProperty("appsecretkey", encryptedAppKey);
					con.setDoOutput(true);
//					String jsonInputString = encryptedClientSecret;
					String jsonResponse = null;
					StringBuilder response = new StringBuilder();

					// String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";

					System.out.println("jsonInputString:" + jsonInputString);

					try (OutputStream os = con.getOutputStream()) {
						byte[] input = jsonInputString.getBytes("utf-8");
						os.write(input, 0, input.length);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
						String responseLine = null;
						while ((responseLine = br.readLine()) != null) {
							response.append(responseLine.trim());
						}
						System.out.println("response:"+response.toString());
						//System.out.println(response.getString("slogan"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return response.toString();

	}
	  public static String decryptSEK( final Map<String, String> mapMode) {
	        try {
	            final byte[] appKeyBytes = mapMode.get("appkey").getBytes(StandardCharsets.UTF_8);
	            final byte[] decryptedTextBytes = com.cgtmse.bdo.AESEncryptionUtility.decrypt(mapMode.get("sek"), appKeyBytes);
	            return Base64.encodeBase64String(decryptedTextBytes);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
	        }
	    }
	  public static String encryptPayload(final String payload, final String decryptedSek) throws Exception {
	        final byte[] sekByte = Base64.decodeBase64(decryptedSek.replaceAll(" ", "+").trim());
	        final Key aesKey = new SecretKeySpec(sekByte, "AES");
	        try {
	            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(1, aesKey);
	            final byte[] encryptedjsonbytes = cipher.doFinal(payload.getBytes());
	            final String encryptedJson = Base64.encodeBase64String(encryptedjsonbytes);
	            return encryptedJson;
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return "Exception " + e;
	        }
	    }
	
	public static String callGenerateIrn(String encryptedResponseData, String bdo_authtoken, String client_id) throws IOException {
		
		
		URL url = null;
		try {
			
				url = new URL("https://einvoiceapi.bdo.in/bdoapi/public/generateIRN");
//			url = new URL("https://sandboxeinvoiceapi.bdo.in/bdoapi/public/generateIRN");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (Exception e) {
            e.printStackTrace();
//            return e.getMessage();
        }
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("client_id", client_id);
		con.setRequestProperty("bdo_authtoken", bdo_authtoken);
		con.setRequestProperty("action", "GENIRN");
		con.setDoOutput(true);
		// String jsonInputString = "{\"clientid\":\"" + clientid. +
		// "\",\"clientsecretencrypted\":\"" + + "\",\"appsecretkey\":\""+ +"}";
		String jsonInputString = encryptedResponseData;
		String jsonResponse = null;
		StringBuilder response = new StringBuilder();

		// String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";

		System.out.println("ResponsePayload:" + jsonInputString);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			//System.out.println(response.getString("slogan"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();

	}
	public static String decryptResponse(final String encryptedResponseData, final String decryptedSek) {
        try {
            final byte[] encKeyBytes = Base64.decodeBase64(decryptedSek.replaceAll(" ", "+").trim());
            final byte[] decryptedTextBytes = com.cgtmse.bdo.AESEncryptionUtility.decrypt(encryptedResponseData, encKeyBytes);
            return new String(decryptedTextBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
	
	public static String cancelIrn(String invoiceNo,String bdo_authtoken,String client_id,String encryptPayload ) throws Exception {
		
		 
		URL URL = null;
		try {
			URL = new URL("https://einvoiceapi.bdo.in/bdoapi/public/cancelIRN");
//			URL = new URL("https://sandboxeinvoiceapi.bdo.in/bdoapi/public/cancelIRN");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("api hit url error:"+e1.getMessage());
			e1.printStackTrace();
		}
		HttpURLConnection con = (HttpURLConnection) URL.openConnection();
		try {
			con.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   		
//	String client_id=null,bdo_authtoken=null;
//	String invoiceId = IrnDataImpl.getInvoiceId(invoiceNo);
	
	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("client_id", client_id);
		con.setRequestProperty("bdo_authtoken", bdo_authtoken);
		con.setRequestProperty("action", "CANIRN");
		
		con.setDoOutput(true);
		String jsonInputString = encryptPayload;
		String jsonResponse = null;
		StringBuilder response = new StringBuilder();

		// String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";

		System.out.println("jsonInputString:" + jsonInputString);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println("response:"+response.toString());
			//System.out.println(response.getString("slogan"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();

}
	public static String getGenerateIrn(String docnum,String docdate, String bdo_authtoken, String client_id) throws Exception {
		// TODO Auto-generated method stub
//		return null;
		URL URL = null;
		try {
//			URL = new URL("https://sandboxeinvoiceapi.bdo.in/bdoapi/public/irnbydocdetails?doctype=INV&docnum="+docnum+"&docdate="+docdate);
			URL = new URL("https://einvoiceapi.bdo.in/bdoapi/public/irnbydocdetails?doctype=INV&docnum="+docnum+"&docdate="+docdate);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("api hit url error:"+e1.getMessage());
			e1.printStackTrace();
		}
//		try {
		HttpURLConnection con = (HttpURLConnection) URL.openConnection();
//		}catch(URLException e) {
//			e.
//		}
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   		
//	String client_id=null,bdo_authtoken=null;
//	String invoiceId = IrnDataImpl.getInvoiceId(invoiceNo);
	
	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("client_id", client_id);
		con.setRequestProperty("bdo_authtoken", bdo_authtoken);
		con.setRequestProperty("gstin", "27AAATC2613D1ZC");
		
		con.setDoOutput(true);
//		String jsonInputString = encryptPayload;
		String jsonResponse = null;
		StringBuilder response = new StringBuilder();

		// String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";

//		System.out.println("jsonInputString:" + jsonInputString);

//		try (OutputStream os = con.getOutputStream()) {
//			byte[] input = jsonInputString.getBytes("utf-8");
//			os.write(input, 0, input.length);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println("Get IRN response:"+response.toString());
			//System.out.println(response.getString("slogan"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();
	}
	
}
