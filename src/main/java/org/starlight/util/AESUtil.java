package org.starlight.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author 黑色的小火苗
 */
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ENCODING = "UTF-8";

    /**
     * aes-cbc128加密 加密数据
     * @param data 要加密的数据
     * @param key 密钥
     * @param iv 向量
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(ENCODING));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(ENCODING));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * aes-cbc128加密 解密数据
     * @param encryptedData 要解密的数据
     * @param key 密钥
     * @param iv 向量
     * @return 解密后的数据
     */
    public static String decrypt(String encryptedData, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(ENCODING));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, ENCODING);
    }


    /**
     * 案例Demo
     */
    public static void main(String[] args) throws Exception {
        String key = "1234567812345678";
        String value = "1234567812345678";
        System.out.println(encrypt("123456789", key, value));
        System.out.println(decrypt("avqPphXzV3qws26Dooa6Yw==", key, value));
    }


}
