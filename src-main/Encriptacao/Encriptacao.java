package Encriptacao;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Encriptacao {
    private static final String ALG = "AES";
    private static final String keyValue = "jG7zvqh/HYkj0jUVCTqSQA==";

    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String encrypt(final String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, convertStringToSecretKey(keyValue));
            byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            System.out.println("Error - Decrypt - [" + e + "].");
        }

        return "";
    }

    public static String decrypt(final String encryptedString) {

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, convertStringToSecretKey(keyValue));
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            System.out.println("Error - Decrypt - [" + e + "].");
        }

        return "";
    }
}
