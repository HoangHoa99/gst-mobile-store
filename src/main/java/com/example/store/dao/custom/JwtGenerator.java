package com.example.store.dao.custom;

import com.example.store.constant.AppConstant;
import com.example.store.dao.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    public String returnToken(User user){
        return Jwts.builder()
                .claim("name", user.getName())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS512, AppConstant.JWT_SECRET)
                .compact();
    }
}
