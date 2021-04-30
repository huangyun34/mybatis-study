package com.my.mybatis.utils;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESUtils {

    /**
     * AES-128-CBC加密模式时,需要用到一个16位的向量
     */
    private static final String IV_PARAMETER = "FRJ9op4MKgTvfBEA";

    /**
     * 加密秘钥
     */
    private static final String SECRET_KEY = "Kw9d$wx[NNL^$9qa";

    private static final String KEY_AES = "AES";

    public static String encrypt(String source) throws Exception {
        return aesCbcEncrypt(SECRET_KEY, source);
    }

    public static String decrypt(String secretContent) throws Exception {
        return decrypt(secretContent, SECRET_KEY, IV_PARAMETER);
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("134");
        System.out.println(decrypt(encrypt));
    }


    /**
     * AES-128-CBC加密.
     *
     * @param secretKey 密钥
     * @param content    需要加密的字符串
     * @return
     */
    private static String aesCbcEncrypt(String secretKey, String content) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_AES);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String decrypt(String src, String key, String iv) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] encrypted1 = Base64.getDecoder().decode(src);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, "utf-8");
    }
}
