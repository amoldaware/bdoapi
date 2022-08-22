package com.cgtmse.bdo;
import java.security.GeneralSecurityException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.LoggerFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.slf4j.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;

public class AESEncryptionUtility
{
    public static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static final String AES_ALGORITHM = "AES";
    public static final int ENC_BITS = 256;
    public static final String CHARACTER_ENCODING = "UTF-8";
    private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    private static KeyGenerator keygen;
    public static final String EXCEPTION = "Exception :: {}";
    private static final Logger logger;
    
    private AESEncryptionUtility() {
    }
    
    public static String encodeBase64String(final byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }
    
    public static byte[] decodeBase64StringTOByte(final String stringData) {
        return Base64.getDecoder().decode(stringData.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String encryptEK(final byte[] plainText, final byte[] secret) {
        try {
            final SecretKeySpec sk = new SecretKeySpec(secret, "AES");
            AESEncryptionUtility.encryptCipher.init(1, sk);
            return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(AESEncryptionUtility.encryptCipher.doFinal(plainText));
        }
        catch (Exception e) {
            AESEncryptionUtility.logger.error("Exception :: {}", (Throwable)e);
            return "";
        }
    }
    
    public static byte[] decrypt(final String plainText, final byte[] secret) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final SecretKeySpec sk = new SecretKeySpec(secret, "AES");
        AESEncryptionUtility.decryptCipher.init(2, sk);
        return AESEncryptionUtility.decryptCipher.doFinal(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(plainText));
    }
    
    public static byte[] decryptCred(final String plainText, final byte[] secret) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final SecretKeySpec sk = new SecretKeySpec(secret, "AES");
        AESEncryptionUtility.decryptCipher.init(2, sk);
        return AESEncryptionUtility.decryptCipher.doFinal(plainText.getBytes());
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)AESEncryptionUtility.class);
        try {
            AESEncryptionUtility.encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            AESEncryptionUtility.decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            (AESEncryptionUtility.keygen = KeyGenerator.getInstance("AES")).init(256);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException ex2) {
            final GeneralSecurityException ex = null;
            final GeneralSecurityException e = ex;
            AESEncryptionUtility.logger.error("Exception :: {}", (Throwable)e);
        }
    }
}