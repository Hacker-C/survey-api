package com.cg.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    private static String secret= "security";


    private static Long expiration =  24L * 60 * 60 * 1000;

    /**
     * 创建token
     * @param userId 用户ID
     * @return
     */
    public static  String generateToken(String userId) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();

    }


    /**
     * 从token中获取用户Id
     * @param token
     * @return
     */
    public static String getUserIdFromToken(String token){
        return getTokenBody(token).getSubject();
    }


    /**
     *  是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }


    /**
     * 获取token中的信息
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }



}
