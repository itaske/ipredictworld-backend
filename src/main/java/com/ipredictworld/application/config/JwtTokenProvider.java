package com.ipredictworld.application.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);



    public String generateToken(Authentication authentication) throws UnsupportedEncodingException {

        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        return JWT.create().withSubject(userPrincipal.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET));



    }

    public String getUserIdFromJWT(String token) throws UnsupportedEncodingException {
       String subject = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET))
               .build()
               .verify(token)
               .getSubject();


        return subject;
    }

    public boolean validateToken(String authToken){

        try{

            System.out.println("Starting to validating token");
            JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET))
                    .build()
                    .verify(authToken);
            System.out.println("Done validating Token");

            return true;
        }catch(AlgorithmMismatchException a){
            a.printStackTrace();
        }catch(InvalidClaimException i){
            i.printStackTrace();
        }catch(TokenExpiredException t) {
            t.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
