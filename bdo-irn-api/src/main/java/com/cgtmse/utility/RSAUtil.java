package com.cgtmse.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCylTWpdayhTa4BgsHoXlHx9EGWMGvw4lIQL3GyQnukc3T6eKadxQZ11nzQX8InwMf+EB8sEzMnq/DQpCX0EAwpPUw3RO1oxAd8nHyEQmrDK9+SjQ49zhvYmYqg8yMEZEqoBz06J0Fd/il1z4ppufeRP0gpatBk6UPXS7VSqRJwBwIDAQAB";
			
//    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKVNal1rKFNrgGCweheUfH0QZYwa/DiUhAvcbJCe6RzdPp4pp3FBnXWfNBfwifAx/4QHywTMyer8NCkJfQQDCk9TDdE7WjEB3ycfIRCasMr35KNDj3OG9iZiqDzIwRkSqgHPTonQV3+KXXPimm595E/SClq0GTpQ9dLtVKpEnAHAgMBAAECgYBPb8/O8Wk41zTvtxpKIqqQ1fXWuPNK02Ftka0hfjFBlZoTUFfNFjcpc59RkUbsJ/TOLp2vHVtD6lfUdy/qwLinYUCbIgD2C0Hy9ZrjV686p25UXGIMu4PGi9onOjsXD1WO3KOtLn1wteyXcBCOfEsSCQ5d0EJzALrMFfPHwDf6AQJBANh7HsyzgzqtMCs0PyDfZC7pVDiOa0lgxbWf/xwKsRAD71G3/tBlpqvDGa3aOzYF1ww35OtEDQctV4Gw6Mu1BCkCQQDTLvwQkVxL4cLSY972GxZJ4TRmkXInSl7oICM07pWOVaGBpt3ibUlG5vHwcPebCyxkzHvehvm3iMH12yAQgtivAkEAq6xXrHXfZ7TYMirotSTyydD3nTI0GqjHoVUUdiR0bgLw0plbVeCG4EeEIb6iniDbfTEGNRxuSHbIomys8H7IoQJBAMYY+NIByPZVXA9HJ8rsNcgvvJvKK663kLdmDiCE/T17Dfk0skbIBUjBMoo9Lcb+gDI9rqRbMLv9SRR67GUQmHkCQB42LRURl+BTUrk8jh8rZ2cUoUL/fmU27nGSIZ4jKEh7uYPzEgGcxCXGzx/w6kTR1q4XvJ4b1Irh3RBgoiMM5Q4=";

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

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

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("6P+tBbYhd480XAe34JEY8z4NJZAH2DlizQJDEd0QQLJocSZkfJib5NlvbT/kv80T", publicKey));
            System.out.println(encryptedString);
            
            //String decryptedString = RSAUtil.decrypt(encryptedString, );
//            
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}


