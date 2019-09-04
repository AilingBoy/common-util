package com.cn.stardust.star.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

/**
 * https://github.com/oraclexing
 * <p>
 *  AES 加解密
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class AESUtil {

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key 加密密码
     * @return 加密结果字符串
     */
    public static String encrypt(String data, String key) {
        if(null == data){
            return null;
        }
        try {
            return doEncrypt(data,key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key 解密密钥
     * @return 解密后结果字符串
     */
    public static String decrypt(String data, String key) {
        if(null == data){
            return null;
        }
        try {
            return doDecrypt(data,key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    private static String doEncrypt(String content, String encryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(content.getBytes());
        keyGenerator.init(128,secureRandom);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        return Base64.encodeBase64String(b);
    }

    /**
     * 解密
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    private static String doDecrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(encryptStr.getBytes());
        keyGenerator.init(128,secureRandom);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}