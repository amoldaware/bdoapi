package com.cgtmse.bdo;

import org.slf4j.LoggerFactory;
import java.math.BigInteger;
import sun.security.util.DerValue;
import java.security.spec.RSAPrivateCrtKeySpec;
import sun.security.util.DerInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import org.apache.tomcat.util.codec.binary.Base64;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.X509EncodedKeySpec;
import java.security.PublicKey;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.nio.file.Files;
import java.io.File;
import java.security.PrivateKey;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BDOClientKeyValidatorAES
{
    private static final Logger log;
    private Cipher cipher;
    public static final String UTF8 = "UTF-8";
    
    public BDOClientKeyValidatorAES() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
    }
    
    public PrivateKey getPrivate(final String filename) throws Exception {
        try {
            final byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
        catch (Exception e) {
            throw new Exception("Exception Occrred in getPrivate() in  GSPBDOClientKeyValidatorAES.class due to :" + e.toString(), e);
        }
    }
    
    public PrivateKey getPrivateOpenSSL(final String filename) throws Exception {
        try {
            final byte[] keyBytes = Files.readAllBytes(Paths.get(filename, new String[0]));
            final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
        catch (Exception e) {
            throw new Exception("Exception Occrred in getPrivateOpenSSL() in  GSPBDOClientKeyValidatorAES.class due to :" + e.toString(), e);
        }
    }
    
    public PublicKey getPublic(final String filename) throws Exception {
        try {
            final byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            final X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
        catch (Exception e) {
            throw new Exception("Exception Occrred in getPublic() in  GSPBDOClientKeyValidatorAES.class due to :" + e.toString(), e);
        }
    }
    
    public void encryptFile(final byte[] input, final File output, final PrivateKey key) throws GeneralSecurityException, Exception {
        this.cipher.init(1, key);
        this.writeToFile(output, this.cipher.doFinal(input));
    }
    
    public void decryptFile(final byte[] input, final File output, final PublicKey key) throws GeneralSecurityException, Exception {
        this.cipher.init(2, key);
        this.writeToFile(output, this.cipher.doFinal(input));
    }
    
    private void writeToFile(final File output, final byte[] toWrite) throws Exception {
        try (final FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(toWrite);
            fos.flush();
        }
        catch (Exception e) {
            BDOClientKeyValidatorAES.log.error("Exception Occurred in writeToFile() inside  GSPBDOClientKeyValidatorAES.class");
            throw new Exception("Exception Occrred in writeToFile() in  GSPBDOClientKeyValidatorAES.class due to :" + e.toString(), e);
        }
    }
    
    public String encryptTextWithPrivateKey(final String msg, final PrivateKey key) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(1, key);
        return Base64.encodeBase64String(this.cipher.doFinal(msg.getBytes("UTF-8")));
    }
    
    public String decryptTextWithPublicKey(final String msg, final PublicKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(2, key);
        return new String(this.cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }
    
    public String encryptTextWithPublicKey(final String msg, final PublicKey key) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(1, key);
        return Base64.encodeBase64String(this.cipher.doFinal(msg.getBytes("UTF-8")));
    }
    
    public String decryptTextWithPrivateKey(final String msg, final PrivateKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(2, key);
        return new String(this.cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }
    
    public static String getKey(final String filename) throws Exception, IOException {
        String strKeyPEM = "";
        try (final BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                strKeyPEM = strKeyPEM + line + "\n";
            }
        }
        catch (Exception e) {
            BDOClientKeyValidatorAES.log.error("Exception Occurred inside getKey() of GSPBDOClientKeyValidatorAES.class ");
            throw new Exception("Exception Occurred inside getKey() of GSPBDOClientKeyValidatorAES.class ");
        }
        return strKeyPEM;
    }
    
    public static PrivateKey getPrivateKey(final String filename) throws Exception {
        try {
            final String privateKeyPEM = getKey(filename);
            return getPrivateKeyFromString(privateKeyPEM);
        }
        catch (Exception e) {
            throw new Exception("\u00cbxception Occurred in getPrivateKey()  in GSPBDOClientKeyValidatorAES.class due to " + e.toString());
        }
    }
    
    public static PrivateKey getPrivateKeyFromString(final String key) throws IOException, GeneralSecurityException {
        final String privateKeyPEM = key;
        final String PEM_PRIVATE_START = "-----BEGIN PRIVATE KEY-----";
        final String PEM_PRIVATE_END = "-----END PRIVATE KEY-----";
        final String PEM_RSA_PRIVATE_START = "-----BEGIN RSA PRIVATE KEY-----";
        final String PEM_RSA_PRIVATE_END = "-----END RSA PRIVATE KEY-----";
        String privateKeyPem = privateKeyPEM;
        if (privateKeyPem.indexOf("-----BEGIN PRIVATE KEY-----") != -1) {
            privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
            privateKeyPem = privateKeyPem.replaceAll("\\s", "");
            final byte[] pkcs8EncodedKey = java.util.Base64.getDecoder().decode(privateKeyPem);
            final KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(new PKCS8EncodedKeySpec(pkcs8EncodedKey));
        }
        if (privateKeyPem.indexOf("-----BEGIN RSA PRIVATE KEY-----") == -1) {
            return null;
        }
        privateKeyPem = privateKeyPem.replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "");
        privateKeyPem = privateKeyPem.replaceAll("\\s", "");
        final DerInputStream derReader = new DerInputStream(java.util.Base64.getDecoder().decode(privateKeyPem));
        final DerValue[] seq = derReader.getSequence(0);
        if (seq.length < 9) {
            throw new GeneralSecurityException("Could not parse a PKCS1 private key.");
        }
        final BigInteger modulus = seq[1].getBigInteger();
        final BigInteger publicExp = seq[2].getBigInteger();
        final BigInteger privateExp = seq[3].getBigInteger();
        final BigInteger prime1 = seq[4].getBigInteger();
        final BigInteger prime2 = seq[5].getBigInteger();
        final BigInteger exp1 = seq[6].getBigInteger();
        final BigInteger exp2 = seq[7].getBigInteger();
        final BigInteger crtCoef = seq[8].getBigInteger();
        final RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
        final KeyFactory factory2 = KeyFactory.getInstance("RSA");
        return factory2.generatePrivate(keySpec);
    }
    
    public static PublicKey getPublicKey(final String filename) throws Exception {
        try {
            final String publicKeyPEM = getKey(filename);
            return getPublicKeyFromString(publicKeyPEM);
        }
        catch (Exception e) {
            throw new Exception("Exception Occurred in getPublicKey() in GSPBDOClientKeyValidatorAES.class due to :" + e.toString());
        }
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
        log = LoggerFactory.getLogger((Class)BDOClientKeyValidatorAES.class);
    }
}