package com.cafe.cafe_management_backend.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String secret ="abcdasdasdwqdqfefg";


    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private String createToken(Map<String, Object> claims, String subject){

        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))// the token will be expired in 10 hours
                .signWith(SignatureAlgorithm.HS256,secret).compact();

    }

    public String generateToken(String username,String role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }
    public String extractUsername(String token){
     return extractClamis(token,Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClamis(token,Claims::getExpiration);
    }
    public <T> T extractClamis(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //may be bug is here
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
//        System.out.println("validateToken token username: "+username);
//        System.out.println("  userdetail userName"+userDetails.getUsername());//why?not email

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//    private Key getSignKey(){
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

}
