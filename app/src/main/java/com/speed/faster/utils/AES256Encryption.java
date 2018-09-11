package com.speed.faster.utils;

//我是代码
import android.util.Base64;
import android.util.Log;

import com.speed.faster.Content;

import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 密码加密解密工具类
 * 设置登录及钱包支付密码要用
 * 采用的是Base64+AES加密方式
 * Constant.RES_KEY 和服务器上的一样,要和服务器做交互  key 是base64后的 加密后的串也是base64后的
 */
public class AES256Encryption {

    /**
     * 密钥算法
     * java6支持56位密钥，bouncycastle支持64位
     * */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     * */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    public static final String ALGORITHM_DES = "AES/CBC/PKCS5Padding";
    /**
     * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
     * @return byte[] 二进制密钥
     * */
    private static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
        // 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
        kg.init(256);
        //  kg.init(128);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return secretKey.getEncoded();
        // 为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
        //  back_return new byte[] { 0x08, 0x08, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c, 0x01, 0x03, 0x09, 0x07, 0x0c, 0x03, 0x07,
        //      0x0a, 0x04, 0x0f, 0x06, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x01, 0x09, 0x06, 0x07, 0x09, 0x0d };
    }

    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return Key 密钥
     * */
    private static Key toKey(byte[] key) throws Exception {

        // 实例化DES密钥
        // 生成密钥
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密后的数据
     * */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {

        // 还原密钥
        Key k = toKey(key);
        /**
         * 实例化
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "AES");
        // 初始化，设置为加密模式
        byte[] srcIv= new byte[16];
        IvParameterSpec iv = new IvParameterSpec(srcIv);
        AlgorithmParameterSpec paramSpec = iv;
        cipher.init(Cipher.ENCRYPT_MODE, k,iv);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密后的数据
     * */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {

        // 欢迎密钥
        Key k = toKey(key);
        /**
         * 实例化
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
        // 初始化，设置为解密模式
        byte[] srcIv= new byte[16];
        IvParameterSpec iv = new IvParameterSpec(srcIv);
        AlgorithmParameterSpec paramSpec = iv;
        cipher.init(Cipher.DECRYPT_MODE, k,paramSpec);
        // 执行操作
        return cipher.doFinal(data);
    }


    /**
     * 解密
     * @author Guocg
     * @param newStr  base64后的 ，从服务器获取解密成字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String newStr) throws Exception {

        return new String(
                AES256Encryption.decrypt(Base64.decode(newStr,Base64.DEFAULT), Base64.decode(Content.AES_SECRET,Base64.DEFAULT)),
                "utf-8");
    }

    /**
     * 加密
     * @author Guocg
     * @param oldStr，本地加密上传
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String oldStr) throws Exception {

        return Base64
                .encodeToString(AES256Encryption.encrypt(oldStr.getBytes(), Base64.decode(Content.AES_SECRET,Base64.DEFAULT)),Base64.DEFAULT);
    }


    //测试
    public static void cesi() throws Exception {
        String oldStr = "qinfens撒打发第三方";
        Log.i("加密", "原文："+oldStr);
        String key;
//      key = Base64.encodeToString(AES256Encryption.initkey(),Base64.DEFAULT);

//      key = "ivX1m+Ya01lNbJ0kZpuwmcfzDVDohK4Bsu9xaY5NrB8=";
//      Log.i("加密", "密钥："+key);

        // 加密数据
        String newStr = encrypt(oldStr);
        Log.i("加密", "加密后："+newStr);

        // 解密数据
        String str = decrypt(newStr);
        Log.i("加密","解密后："+str);
    }
}
