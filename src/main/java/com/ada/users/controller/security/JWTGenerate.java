package com.ada.users.controller.security;

import com.ada.users.entity.UserDocument;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTGenerate {
    private  String KEY = "M1lt0n4gudel0";
    public String generateToken(UserDocument userDocument){
        return Jwts.builder().setSubject(userDocument.getEmail()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *2))
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();

    }

    public boolean validateToken(String token, UserDocument userDocument){
        return userDocument.getEmail().equals(getEmail(token)) && !isTokenEpired(token);
    }

    public String getEmail(String token) {
        return getClaim(token).getSubject();
    }


    public boolean isTokenEpired(String token) {
        return getClaim(token).getExpiration().before(new Date());
    }

    public Claims getClaim(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
