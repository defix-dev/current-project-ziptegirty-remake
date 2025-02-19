package org.ziptegrity.services.chat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ChatCryptUtils {
    public static String encryptMessage(String message) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(ChatConstants.MESSAGE_CRYPT_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt: "+e.getMessage());
        }
    }

    public static String decryptMessage(String message) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(ChatConstants.MESSAGE_CRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(decryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt: "+e.getMessage());
        }
    }

    public static SecretKeySpec getSecretKeySpec() {
        String key = System.getenv(ChatConstants.MESSAGE_SECRET_KEY);
        if(key == null) throw new IllegalArgumentException("Message secret key not found.");
        return new SecretKeySpec(Base64.getDecoder().decode(key), ChatConstants.MESSAGE_CRYPT_ALG);
    }
}
