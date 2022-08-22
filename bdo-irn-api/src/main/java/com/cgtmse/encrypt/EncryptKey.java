package com.cgtmse.encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Utilities;
import org.apache.commons.codec.binary.Base64;
//import Decoder.BASE64Decoder;

public class EncryptKey {
	private static Cipher encryptCipher;
	private Cipher cipher;
    private static Cipher decryptCipher;
	static SecretKey secret = null;
	public static final String CHARACTER_ENCODING = "UTF-8";
	/*
	public static String encryptAsymmentricKey(String clearText) throws Exception {
		PublicKey publicKeys = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
//		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
		byte[] encryptedText = cipher.doFinal(clearText.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedPassword;
	}*/
	/* public static PublicKey getPublicKey(String filename)
		        throws GeneralSecurityException, FileNotFoundException
		    {
		        String publicKeyPEM = getKey(filename);
		        return getPublicKeyFromString(publicKeyPEM);
		    }
*/
		    public static PublicKey getPublicKeyFromString(String key)
		        throws GeneralSecurityException
		    {
		        String publicKeyPEM = key;
		        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
		        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
		        byte encoded[] = Base64.decodeBase64(publicKeyPEM);
		        KeyFactory kf = KeyFactory.getInstance("RSA");
		        return kf.generatePublic(new X509EncodedKeySpec(encoded));
		    }

	public String encryptTextWithPublicKey(String msg, PublicKey key)
	        throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
	    {
		Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
	        cipher1.init(Cipher.ENCRYPT_MODE, key);
	        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
	    }
	public static PublicKey getPublicKey()
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// FileInputStream in = new
		// FileInputStream("C:\\WIP\\Invoice\\einv_sandbox.pem");//UAT
		FileInputStream in = new FileInputStream("C:\\Sandbox_Public Keys\\Sandbox\\BDOpublicKey.pem");// UAT

		// FileInputStream in = new
		// FileInputStream("C:\\WIP\\Invoice\\ProdPubKey3.pem");//UAT

		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String pubKey = new String(keyBytes, "UTF-8");
		pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
//        BASE64Decoder decoder = new BASE64Decoder();
		Base64 decoder = new Base64();
		byte encoded[] = Base64.decodeBase64(pubKey);
//		keyBytes = decoder.decodeBuffer(pubKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		//KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);
		return publicKey;
	}
	public static byte[] createAESKey() {
		byte[] appKey = null;
		try {
			KeyGenerator gen = KeyGenerator.getInstance("AES");
			gen.init(192);
			secret = gen.generateKey();
			appKey = secret.getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
		}
		return appKey;
	}
	/*
    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
*/
   /* public static String decryptBySymmentricKey(String data, String decryptedSek) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
        Key aesKey = new SecretKeySpec(sekByte, "AES");
         try {
              Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//              Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
              cipher.init(Cipher.DECRYPT_MODE, aesKey);
              byte[] decordedValue = new BASE64Decoder().decodeBuffer(data);
              System.out.println("decordedValue:"+decordedValue);
              byte[] decValue = cipher.doFinal(decordedValue);
              System.out.println("decValue:"+decValue);
              return new String(decValue);
          } catch (Exception e) {
              return "Exception " + e.getMessage();
           }
       }
    */
    public static byte[] decrypt(final String plainText, final byte[] secret) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final SecretKeySpec sk = new SecretKeySpec(secret, "AES");
        EncryptKey.decryptCipher.init(2, sk);
        return EncryptKey.decryptCipher.doFinal(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(plainText));
    }
    /*
	public static String decrptyBySyymetricKey(String encryptedSek, SecretKey secret) throws BadPaddingException
	{
		//String ss1="N/231wLDHQ28qlavayEEbP3eqvBFLSky";
		//SecretKey ss=ss1;
		System.out.println("secret:::::"+secret);
	//	System.out.println("decrptyBySyymetricKey method called form TaxReportAction==");
		Key aesKey = new SecretKeySpec(secret.getEncoded(), "AES"); // converts bytes(32 byte random generated) to key
		System.out.println("aeskey:::"+aesKey);
		try {
			// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS5Padding");
		      //  cipher.init(Cipher.DECRYPT_MODE, aesKey);
		        //return new String(cipher.doFinal(data));
		        
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // encryption type = AES with padding PKCS5
			cipher.init(Cipher.DECRYPT_MODE, aesKey); // initiate decryption type with the key
		System.out.println("tttttttt");
			byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek); // decode the base64 encryptedSek to bytes
			System.out.println("encryptedSekBytes:"+encryptedSekBytes);
				
			
			byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes); // decrypt the encryptedSek with the initialized cipher containing the key(Results in bytes)
		   
			 System.out.println("tttttttt11:"+decryptedSekBytes);
				
			String decryptedSek = Base64.getEncoder().encodeToString(decryptedSekBytes); // convert the decryptedSek(bytes) to Base64 StriNG
		    System.out.println("tttttttt222");
			
			//String decryptedSek = new String(base64.encode(decryptedSekBytes));
			//decryptedSek="gzqTLzRf0NMlesHfpSQItjdrJEqweMW2CFxqho5a3SU=";
			return decryptedSek; // return results in base64 string
		}catch(Exception e)
		{
			return "Exception; "+e;
		}
	}
	
	/*
	
	//Base 64 decrypted response in json string using invoice JSON and Sek
		public static String decryptBySymmentricKey(String data, String decryptedSek) {
			
			System.out.println("sekByte11111111:"+decryptedSek);
			
			
			byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
			
			System.out.println("sekByte:"+decryptedSek);
			Key aesKey = new SecretKeySpec(sekByte, "AES");
			System.out.println("ttttttt:"+aesKey.toString());
			
			//aesKey.
			try {
				System.out.println("in try");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				System.out.println("in cipher");
				cipher.init(Cipher.DECRYPT_MODE, aesKey);
				System.out.println("in init");
				
				System.out.println("aaaaaaa"+aesKey);
			//	   public static byte[] decodeBase64StringTOByte(String stringData) throws Exception {
				byte[] decordedValue= java.util.Base64.getDecoder().decode(data.getBytes(CHARACTER_ENCODING));
				//	}

			//	byte[] decordedValue = Base64.getDecoder().decode(data.getBytes(CHARACTER_ENCODING));
				System.out.println("decordedValue:"+decordedValue);
				byte[] decValue=null;
				try {
				 decValue = cipher.doFinal(decordedValue);
				System.out.println("decValue:"+decValue);
				}catch(Exception e) {
					System.out.println("decValue try catch :"+e.getMessage());
				}
				return new String(decValue);
			} catch (Exception e) {
				return "Exception " + e;
			}
		}
		*/
    /*
		public static SecretKey convertStringToSecretKeyto(String encodedKey) {
		    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		    return originalKey;
		}
		*/
		public static void main(String args[]) {
			String secret="N/231wLDHQ28qlavayEEbP3eqvBFLSky";
//			SecretKey ss=convertStringToSecretKeyto(secret);
			//System.out.println("sss:"+decryptBySymmentricKey());
					
			String decrypted_appkey = "jpIbAf+xmrvvklK6MrDSGvWNCA0eD2bE";
			String receivedSEK = "6P+tBbYhd480XAe34JEY8z4NJZAH2DlizQJDEd0QQLJocSZkfJib5NlvbT/kv80T";
			
//			decryptBySymmentricKey(decrypted_appkey,receivedSEK);
		}

}
