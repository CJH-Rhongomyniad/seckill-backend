package com.example.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAqQEHRekC6Kew2NUBlce1BE4QucndOhxvqPnkJcMTDNacuMub\n" +
            "kDex9sZXUPZGyH8xQLZQ+zJf51LY0wpCPcNonsme22LGjOlckPjsqUiWtx+kfFYO\n" +
            "-----END RSA PRIVATE KEY-----\n";

    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    public static String decrypt(String encryptedData) throws Exception {
//        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//        return new String(decryptedBytes);

        try {
            logger.debug("Decrypting data: {}", encryptedData);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Base64 input: {}", encryptedData, e);
            throw e;
        }
    }
}
