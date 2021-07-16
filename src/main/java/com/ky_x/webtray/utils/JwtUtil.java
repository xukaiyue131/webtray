package com.ky_x.webtray.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
import org.slf4j.Logger;



@Component
public class JwtUtil {
    private static String secret ="8ae0d24822ef59d9e75745449b3501bc";

    private static int expire= 15*60*1000;
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static String createToken(int userId,String username){
        DateTime date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 5);
        //加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //设置头信息
        Map<String,Object>map = new HashMap<>();
        map.put("type","jwt");
        map.put("alg","HS256");
        return JWT.create()
                .withAudience(String.valueOf(userId))
                .withClaim("userId",String.valueOf(userId))
                .withExpiresAt(date)
                .withClaim("username",username)
                .sign(algorithm);
    }

    public  static boolean verity(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier build = JWT.require(algorithm).build();
            DecodedJWT verify = build.verify(token);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    public static String getName(String token,String content){
        try{
            Algorithm algorithm=Algorithm.HMAC256(secret);
            JWTVerifier verifier=JWT.require(algorithm).build();
            DecodedJWT jwt=verifier.verify(token);
            return jwt.getClaim(content).asString();
        }catch(Exception e){

        }
        return null;
    }
    //获取签发对象
    public static  String getAudience(String token){
        String audience = null;
        try{
            audience = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){

        }
        return  audience;
    }

    public static int get(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier build = JWT.require(algorithm).build();
            DecodedJWT verify = build.verify(token);
            Integer userId = verify.getClaim("userId").asInt();
            return userId;

        }catch (Exception e){
            logger.info("token过期或者无法获取用户");
        }
        return 0;
    }

}