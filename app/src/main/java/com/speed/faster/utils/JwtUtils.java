package com.speed.faster.utils;


import android.text.TextUtils;
import android.util.Log;


import com.speed.faster.Content;

import org.bouncycastle.util.encoders.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Created by mjm  on 2018/7/9 17:57
 * JWT加密解密算法
 */
public class JwtUtils {

    public static String generateJwt(String deviceId, String appId, String customerId) {
        L.e("JwtUtils--generateJwt--deviceId--" + deviceId + "--appid--" + appId + "--customerId--" + customerId);
        Date var0 = new Date();
        Long var1 = Long.valueOf(var0.getTime());
        Date var2 = new Date(var1.longValue() + 200000L);
        HashMap var3 = new HashMap();
        var3.put("typ", "JWT");
        var3.put("alg", "HS256");
        String var4 = "";
        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("deviceId", deviceId);
        claims.put("appId", appId);
        claims.put("platform", "android");
        if (customerId != null || "".equals(customerId)) {
            claims.put("customerId", customerId);
        }
        try {
            var4 = Jwts.builder()
                    .setHeader(var3)
                    .signWith(SignatureAlgorithm.HS256, (Content.JWT_SECRET).getBytes("UTF-8"))  //两个参数，一个是加密算法，一个秘钥；SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法；
                    .setClaims(claims)
                    .compact();
        } catch (Exception var6) {
            Log.e("HttpRequest", "octo generate error...", var6);
        }
        L.e("header加密--" + var4);
        return var4;
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey((Content.JWT_SECRET).getBytes("UTF-8"))         //设置签名的秘钥
                .parseClaimsJws(jwt)
                .getBody();//设置需要解析的jwt
        L.e("deviceId--" + claims.get("deviceId", String.class));
        L.e("appId--" + claims.get("appId", String.class));
        return claims;
    }

    //一套适用于Android java  AES-JWT-加密
    public static String generateJwtAes() {
        Date var0 = new Date();
        Long var1 = Long.valueOf(var0.getTime());
        Date var2 = new Date(var1.longValue() + 200000L);
        HashMap var3 = new HashMap();
        var3.put("typ", "JWT");
        var3.put("alg", "HS256");
        String var4 = "";
        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("deviceId", "E3FBCA22-6C3F-41F9-B67A-2CB2B641DS8B");
        claims.put("appId", "1375965573");
//        claims.put("customerId", "5605645");
        try {
            var4 = Jwts.builder().
                    setHeader(var3).
//                    setIssuer("").    //请求的发起者；
//                    setIssuedAt(var0).          //请求的发起时间；
//                    setExpiration(var2).        //expTime是过期时间，当前时间+200秒；
//        signWith(SignatureAlgorithm.HS256, (Content.JWT_SECRET).getBytes("UTF-8"))  //两个参数，一个是加密算法，一个秘钥；SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法；
        signWith(SignatureAlgorithm.HS256, generalKey())
                    .setClaims(claims)
                    .compact();
        } catch (Exception var6) {
            Log.e("HttpRequest", "octo generate error...", var6);
        }
        L.e("var4--" + var4);
        return var4;
    }

    //对密钥进行AES加密
    public static SecretKey generalKey() throws Exception {
        byte[] encodedKey = Base64.decode((Content.JWT_SECRET).getBytes("UTF-8"));//本地的密码解码[B@152f6e2
        System.out.println(encodedKey);//[B@152f6e2
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return key;
    }

    //一套适用于Android java  AES-JWT-解密
    public static Claims parseJWTAes(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt)
                .getBody();//设置需要解析的jwt
        L.e("username--" + claims.get("username", String.class));
        L.e("appId--" + claims.get("appId", String.class));
        return claims;
    }

    //一套适用于Android java  AES-JWT-解密
    public static boolean isJwtValid(String jwt) {
        try {
            // 解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    .setSigningKey(Content.JWT_SECRET)  //SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法；
                    .parseClaimsJws(jwt)   //jwt是客户端生成的JWT字符串；
                    .getBody();
            String vaule = claims.get("deviceId", String.class);   //获取自定义字段key；
            L.e("deviceId--" + vaule);
            L.e("appId--" + claims.get("appId", String.class));
            // 判断自定义字段是否正确
            if ("deviceId".equals(vaule)) {
                return true;
            } else {
                return false;
            }
        }
        //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的；
        //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效；
        catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }
}
