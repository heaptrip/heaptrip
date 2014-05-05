package com.heaptrip.service.account.user;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserSecurityTest extends AbstractTestNGSpringContextTests {

    private String myPassword = "12345678";
    private String globalSalt = "m9vbmxudSbDcM/f8yCqbng==";

    private String salt;
    private String hash;

    @Test(enabled = true, priority = 1)
    public void createHash() throws NoSuchAlgorithmException, IOException {
        salt = byteToBase64(generateSalt());
        hash = byteToBase64(generatePassword(myPassword, base64ToByte(salt)));
        System.out.print("salt=" + salt + " hash=" + hash);
    }

//    @Test(enabled = true, priority = 2)
    public void checkHash() throws IOException, NoSuchAlgorithmException {
        String checkedHash = byteToBase64(generatePassword(myPassword, base64ToByte(salt)));
        Assert.isTrue(hash.equals(checkedHash));
    }

    private byte[] generatePassword(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(globalSalt.getBytes("UTF-8"));
        byte[] temp = digest.digest(password.getBytes("UTF-8"));

        digest.reset();
        digest.update(salt);
        return digest.digest(temp);
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * From a base 64 representation, returns the corresponding byte[]
     * @param data String The base64 representation
     * @return byte[]
     * @throws java.io.IOException
     */
    public static byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     * @throws IOException
     */
    public static String byteToBase64(byte[] data){
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }
}
