package com.cgtmse.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
//import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.text.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgtmse.encrypt.EncryptKey;
import com.cgtmse.modal.IrnData;
import com.cgtmse.utility.AESEncryption;
import com.cgtmse.utility.EncryptionUtil;
import com.cgtmse.utility.RSAUtil;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils; 
//org.apache.commons.codec.binary.Base64;
//import Decoder.BASE64Decoder;

@RestController
@RequestMapping("/api/cgtmse")
public class BDOController {
	   static String userName = "cgtmse_mh";    	//BDO Production
		  static String password = "Bdo@123#";  	//Production
		   static String gstin = "27AAATC2613D1ZC";
		
	
	@PostMapping(value = "/get-app-key")
	public String getAppKey() throws Exception {
		//String appKey = Base64.getEncoder().encodeToString(EncryptKey.createAESKey());
		final String appKey = RandomStringUtils.randomAlphanumeric(32);
//        System.out.println("New AppKey : " + appKey + " on :" + LocalDateTime.now());
//        return appKey;
		//String appKey1=AESEncryption.generateSecureKey();
//		  String appKey = RandomStringUtils.randomAlphanumeric(32);
//	        System.out.println((new StringBuilder()).append("New AppKey : ").append(appKey).append(" on :").append(LocalDateTime.now()).toString());
//	        return appKey;
	  
		System.out.println("appkey:" + appKey);
		return appKey;
	}

	@PostMapping(value = "/encryptseckey")
	public String encryptSecretKey(@RequestBody String ecryptData) throws Exception
	{
//		System.out.println(key);
		
		//String payload = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\",\"appkey\":\"" + appKey + "\",\"ForceRefreshAccessToken\": true}";
		 System.out.println("Payload: Plain: " + ecryptData);
       //  payload = Base64.getEncoder().encodeToString(payload.getBytes());
        // String payload = "{\"Data\":\"" + EncryptKey.encryptAsymmentricKey(appKey) + "\"}";
       //  System.out.println("Payload: Encrypted: " + payload);
        
		//String payload = EncryptKey.encryptAsymmentricKey(appKey);
		
		//String payload=EncryptionUtil.encrypt(key);
		// String appKey = Base64.getEncoder().encodeToString(createAESKey());
		//System.out.println("payload:" + payload);
//try {
		  String encText;
	        PublicKey publicKey = EncryptKey.getPublicKey();
	        System.out.println("publicKey:"+publicKey);
	        encText = (new EncryptKey()).encryptTextWithPublicKey(ecryptData.trim(), publicKey);
	        return encText;
	 
//	        Exception e;
//	        e;
//	        e.printStackTrace();
//	        return e.getMessage();
//		return payload;
	}
//	@SuppressWarnings("unused")
	@PostMapping(value = "/decryptseckey", consumes="application/json", produces="application/json")
//	@ResponseBody
	 public String decryptSEK(@RequestBody final Map<String, String> mapMode) {
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
	/*public String decryptSecretKey(@RequestBody DecryptData dec) throws Exception
	
	{
//		 byte decryptedTextBytes[];
//	        byte appKeyBytes[] = ((String)mapMode.get("appkey")).getBytes(StandardCharsets.UTF_8);
//	        decryptedTextBytes = EncryptKey.decrypt((String)mapMode.get("sek"), appKeyBytes);
//	        return Base64.encodeBase64String(decryptedTextBytes);
	       
		String decryptedSek=null;
		try {
			
		//SecretKey secret = null;
//		Gson gson = new Gson();
		//secret=dec.getAppkey();
		//String ss=dec.getAppkey();

		//String appkey=dec.getAppkey();
		//String sek=dec.getSek();
		//System.out.println(dec[0]+"ttttt"+dec[1]);
			
	//	String decrypted_appkey = "wHBER8M3nKC8vocvOC5GSXjhrpj2YTFv";
		//String receivedSEK = "+6d3IhweLBY4bZ7vhyOc2AabFz1qcaIi0zXNOT1Y9uDbIc6tlV+W0PFC/Jzk15ir";
		
		//byte[] authEK = AESEncryption.decrypt(dec.getSek(), AESEncryption.decodeBase64StringTOByte(dec.getAppkey()));
//		DecryptData getResponceObj =  gson.fromJson(dec.toString(),DecryptData.class);
		//JsonDataForToken getResponceObj =  gson.fromJson(Response_json.toString(),JsonDataForToken.class);
//		String sek=getResponceObj.getSek();
//		String secret =  getResponceObj.getAppkey();
		//String payload = EncryptKey.encryptAsymmentricKey(dec.getAppkey());
		//  String decryptedString = RSAUtil.decrypt(dec.getSek(),dec.getAppkey());
		//String appKey = Base64.getEncoder().encodeToString(EncryptKey.createAESKey());
//	SecretKey ss=EncryptKey.convertStringToSecretKeyto(dec.getAppkey());
//		 decryptedSek=EncryptKey.decryptBySymmentricKey(dec.getSek(),dec.getAppkey());
//		 decryptedSek=EncryptKey.decryptBySymmentricKey(dec);  //Base 64 decoded string of decrypted invoice JSON using Sek./
		//System.out.println("decryptedSek:::::::"+dec.getSek());
		//System.out.println("appkey:::::::"+dec.getAppkey());
		//byte[] authEK = AESEncryption.decrypt(dec.getSek(),AESEncryption.decodeBase64StringTOByte(dec.getAppkey()));
		//byte[] authEK = decrypt(receivedSEK, decodeBase64StringTOByte(decrypted_appkey));
		
		//String decryptedSek=EncryptKey.decryptBySymmentricKey(dec.getAppkey(),dec.getSek());
//		System.out.println("decryptedSek:"+decryptedSek);
		//decryptBySymmentricKey
		//Base 64 decoded string of decrypted invoice JSON using Sek./
		//String payload = EncryptKey.encryptAsymmentricKey(key);
		// String appKey = Base64.getEncoder().encodeToString(createAESKey());
		System.out.println("payload:" + decryptedSek);
		}catch(Exception ex)  {
			System.out.println("Batch Exception invoiceGeneration()::"+ex.getMessage());
			ex.printStackTrace();			
		}

//String payload=authEK;
	return decryptedSek;
	}
	*/
//	public void callInvoice() {
//		Gson gson = new Gson();
//		DecryptData getResponceObj =  gson.fromJson(Response_json.toString(),JsonDataForToken.class);
//		
//	}
	
	@PostMapping("/cgtmse-auth")
	public String saveUser(@RequestBody String clientid) throws IOException {
//	Map<String, Object> map = new LinkedHashMap<String, Object>();
//	userService.save(user);
//	map.put("status", 1);
//	map.put("message", "Record is Saved Successfully!");
		System.out.println("clientid:" + clientid);
		URL url = null;
		try {
			url = new URL("https://sandboxeinvoiceapi.bdo.in/bdoauth/bdoauthenticate");
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
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		// String jsonInputString = "{\"clientid\":\"" + clientid. +
		// "\",\"clientsecretencrypted\":\"" + + "\",\"appsecretkey\":\""+ +"}";
		String jsonInputString = clientid;
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

}
