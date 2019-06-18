package com.cn.stardust.star.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

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

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String KEY_AES = "AES";

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
            byte[] content = data.getBytes(DEFAULT_CHARSET);
            return doAES(content, key, Cipher.ENCRYPT_MODE);
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
            byte[] content = parseHexStr2Byte(data);
            return doAES(content, key, Cipher.DECRYPT_MODE);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加解密
     * KeyGenerator和SecureRandom 是无法提升为类的静态变量
     * 多线程下已测试线程不安全
     *
     * @param content 待处理字节数组
     * @param key  密钥
     * @param mode 加解密mode
     * @return
     */
    private static String doAES(byte[] content, String key, int mode) {
        try {
            if (null == content) {
                return null;
            }
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            //3.产生原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，
            // 第二个参数为使用的KEY
            cipher.init(mode, keySpec);
            byte[] result = cipher.doFinal(content);
            if (mode == Cipher.ENCRYPT_MODE) {
                //将二进制转换成16进制
                return parseByte2HexStr(result);
            } else {
                return new String(result, DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("AES 密文处理异常");
        }
        return null;
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}