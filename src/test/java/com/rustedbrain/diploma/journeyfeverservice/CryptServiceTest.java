package com.rustedbrain.diploma.journeyfeverservice;


import com.rustedbrain.diploma.journeyfeverservice.controller.security.CryptService;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Random;

public class CryptServiceTest {

    private static final String ENCODED_STRING = "iuMFqppSWaECOiegIKydmw==";
    private static final String DECODED_STRING = "will this work?";
    private static final int RANDOM_STRING_LENGTH = 16;

    @Test
    public void shouldDecrypt() throws Exception {
        String decoded = CryptService.decrypt(ENCODED_STRING);
        Assert.assertNotEquals(ENCODED_STRING, decoded);
    }

    @Test
    public void shouldEncrypt() throws Exception {
        String encoded = CryptService.encrypt(DECODED_STRING);
        Assert.assertNotEquals(DECODED_STRING, encoded);
    }

    @Test
    public void shouldEncryptAndDecrypt() throws Exception {
        byte[] array = new byte[RANDOM_STRING_LENGTH];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName(CryptService.DEFAULT_CHARSET));

        String encoded = CryptService.encrypt(generatedString);
        String decoded = CryptService.decrypt(encoded);

        Assert.assertNotEquals(generatedString, encoded);
        Assert.assertEquals(generatedString, decoded);
    }
}
