package com.cgtmse.bdo;

import org.slf4j.LoggerFactory;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.tomcat.util.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.util.ResourceUtils;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.nio.file.Files;
import java.io.File;
import java.security.PrivateKey;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import javax.crypto.Cipher;

public class RSAEncryptionUtility
{
   private Cipher cipher;
   private static Logger logger;
   
   public RSAEncryptionUtility() throws NoSuchAlgorithmException, NoSuchPaddingException {
       this.cipher = Cipher.getInstance("RSA");
   }
   
   public PrivateKey getPrivate(final String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
       final byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
       final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
       final KeyFactory kf = KeyFactory.getInstance("RSA");
       return kf.generatePrivate(spec);
   }
   
   public PublicKey getPublic(final String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
       final File f = ResourceUtils.getFile(filename);
       final byte[] keyBytes = Files.readAllBytes(f.toPath());
       final X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
       final KeyFactory kf = KeyFactory.getInstance("RSA");
       return kf.generatePublic(spec);
   }
   
   public String encryptTextWithPublicKey(final String msg, final PublicKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
       this.cipher.init(1, key);
       return Base64.encodeBase64String(this.cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
   }
   
   public static String getKey(final String filename) throws FileNotFoundException {
       final StringBuilder strKeyPEM = new StringBuilder();
       final BufferedReader br = new BufferedReader(new FileReader(filename));
       try {
           String line;
           while ((line = br.readLine()) != null) {
               strKeyPEM.append(line).append("\n");
           }
       }
       catch (IOException e) {
           RSAEncryptionUtility.logger.error("Exception in getKey:: {}", (Throwable)e);
           try {
               br.close();
           }
           catch (IOException e1) {
               RSAEncryptionUtility.logger.error("Exception in getKey catch :: {}", (Throwable)e1);
           }
       }
       finally {
           try {
               br.close();
           }
           catch (IOException e2) {
               RSAEncryptionUtility.logger.error("Exception in getKey catch :: {}", (Throwable)e2);
           }
       }
       return strKeyPEM.toString();
   }
   
   public static PublicKey getPublicKey(final String filename) throws GeneralSecurityException, FileNotFoundException {
       final String publicKeyPEM = getKey(filename);
       return getPublicKeyFromString(publicKeyPEM);
   }
   
   public static PublicKey getPublicKeyFromString(final String key) throws GeneralSecurityException {
       String publicKeyPEM = key;
       publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
       publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
       final byte[] encoded = Base64.decodeBase64(publicKeyPEM);
       final KeyFactory kf = KeyFactory.getInstance("RSA");
       return kf.generatePublic(new X509EncodedKeySpec(encoded));
   }
   
   static {
       RSAEncryptionUtility.logger = LoggerFactory.getLogger((Class)RSAEncryptionUtility.class);
   }
}
