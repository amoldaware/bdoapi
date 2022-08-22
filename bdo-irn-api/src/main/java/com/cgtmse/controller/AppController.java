package com.cgtmse.controller;

//package com.cgtmse.controller;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.tomcat.util.codec.binary.Base64;
//import com.bdoclient.apihelper.util.AESEncryptionUtility;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import org.apache.commons.lang3.RandomStringUtils;
//import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.PublicKey;
//import com.bdoclient.apihelper.util.RSAEncryptionUtility;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
//import com.bdoclient.apihelper.repository.AppMasterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.cgtmse.bdo.RSAEncryptionUtility;

@RestController
@RequestMapping("/api/cgtmsem/")
public class AppController
{
    @Value("${bdo.public.key.path}")
    private String bdoPubKeyPath;
    @Value("${nic.public.key.path}")
     private String nicPubKeyPath;
//    @Autowired
//    private AppMasterRepository appmasterrepo;
    
 
    
    //@ApiOperation("This API will encrypt BDO password using BDO App key {ecryptData}")
    @PostMapping(value="/encryptWithBDOkey")
    public String encryptWithBDOPubKey(@RequestBody String ecryptData) throws Exception{
        try {
            final PublicKey publicKey = RSAEncryptionUtility.getPublicKey(this.bdoPubKeyPath);
            final String encText = new RSAEncryptionUtility().encryptTextWithPublicKey(ecryptData.trim(), publicKey);
            return encText;
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    @GetMapping(value= "/getappkey" )
    //@ApiOperation("This API will encrypt BDO password using BDO App key")
    public String getAppKey() {
        final String appKey = RandomStringUtils.randomAlphanumeric(32);
        System.out.println("New AppKey : " + appKey + " on :" + LocalDateTime.now());
        return appKey;
    }
    
    @PostMapping({ "/encryptWithNICkey" })
//    @ApiOperation("This API will encrypt  password using NIC public key {ecryptData}")
    public String encryptWithNICPubKey(@RequestBody final String ecryptData) throws Exception {
        final String strPublicKeyPath = this.nicPubKeyPath;
        PublicKey publicKey = null;
        String encryptedPassword = null;
        try {
            final com.cgtmse.bdo.RSAEncryptionUtility rsaEncUtil = new com.cgtmse.bdo.RSAEncryptionUtility();
            publicKey = rsaEncUtil.getPublic(strPublicKeyPath);
            encryptedPassword = rsaEncUtil.encryptTextWithPublicKey(ecryptData.trim(), publicKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
    
    @PostMapping({ "/decryptsek" })
//    @ApiOperation("This API will decrypt sek{appkey and  sek}")
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
    
    @PostMapping({ "/encryptPayload" })
//    @ApiOperation("This API will encrypt payload {payload} parameter =decryptedSek")
    public String encryptPayload(@RequestBody final String payload, @RequestParam final String decryptedSek) throws Exception {
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
    
    @PostMapping({ "/decryptResponse" })
//    @ApiOperation("This API will decrypt response data {encryptedResponseData} and parameter = decryptedSek")
    public String decryptResponse(@RequestBody final String encryptedResponseData, @RequestParam final String decryptedSek) {
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
    

    @PostMapping({ "/generateIRN" })
//    @ApiOperation("This API will decrypt response data {encryptedResponseData} and parameter = decryptedSek")
    public String generateIRN(@RequestBody final String encryptedResponseData,@RequestHeader Map<String,String> headers) throws IOException {
        
//    	System.out.println("clientid:" + clientid);
		URL url = null;
		try {
			url = new URL("https://sandboxeinvoiceapi.bdo.in/bdoapi/public/generateIRN");
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
		
		try {
        	headers.forEach((key,value) ->{
        		
        		con.setRequestProperty(key,value);
                System.out.println("Header Name: "+key+" Header Value: "+value);
            });
        	System.out.println("encryptedResponseData:"+encryptedResponseData);
        }
        catch (Exception e) {
            e.printStackTrace();
//            return e.getMessage();
        }
//		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("Accept", "application/json");
//		con.set`
		con.setDoOutput(true);
		// String jsonInputString = "{\"clientid\":\"" + clientid. +
		// "\",\"clientsecretencrypted\":\"" + + "\",\"appsecretkey\":\""+ +"}";
		String jsonInputString = encryptedResponseData;
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