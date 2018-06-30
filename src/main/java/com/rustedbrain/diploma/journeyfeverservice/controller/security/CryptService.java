package com.rustedbrain.diploma.journeyfeverservice.controller.security;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class CryptService {

    public static final String DEFAULT_CHARSET = "UTF8";
    private static final String CIPHER = "Blowfish";
    private static String strkey = "Blowfish";

    //encrypt using blowfish algorithm
    public static String encrypt(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(strkey.getBytes(DEFAULT_CHARSET), CIPHER);
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(DEFAULT_CHARSET)));
    }

    //decrypt using blow fish algorithm
    public static String decrypt(String encrypted) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(encrypted);
        SecretKeySpec key = new SecretKeySpec(strkey.getBytes(DEFAULT_CHARSET), CIPHER);
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encryptedData);
        return new String(decrypted);

    }

    public static void main(String[] args) throws Exception {
        String data = "will this work?";
        String encoded = encrypt(data);
        System.out.println(encoded);
        String decoded = decrypt(encoded);
    }
}
