package com.hug.hug_api.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hug.hug_api.domain.user.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    private static String JWT_SECRET = "trifle";

    public static final long ACCESS_TIME = 60*60*24*100;  // 토큰 유효기간 100일

    public static Algorithm ALGORITHM;

    @PostConstruct
    private void init(){
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        ALGORITHM = Algorithm.HMAC512(JWT_SECRET);
    }

    public static String generateToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond()+ACCESS_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token){

        try{
            DecodedJWT verified = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder()
                    .success(true)
                    .email(verified.getSubject())
                    .build();
        }
        catch(Exception ex){
            return VerifyResult.builder()
                    .success(false)
                    .email("anonymous").build();
        }
    }
}
