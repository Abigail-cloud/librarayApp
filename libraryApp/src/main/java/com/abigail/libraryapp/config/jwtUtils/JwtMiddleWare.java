package com.abigail.libraryapp.config.jwtUtils;

import com.abigail.libraryapp.service.userservices.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Component
public class JwtMiddleWare {
    private final Logger logger = LoggerFactory.getLogger(JwtMiddleWare.class);

    @Value("${abigail.app.jwtsecret}")
    private String jwtSecret;
    @Value("30000")
    private int jwtExpirationMs;
    @Value("${abigail.app.jwtCookieName}")
    private String jwtCookieName;
    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,jwtCookieName);
        if(cookie!= null){
            return cookie.getValue();
        } else{
            return null;
        }
    }
    public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails){
        String jwt = generateTokenFromEmail(userDetails.getEmail());
        return  ResponseCookie.from(jwtCookieName, jwt).path("/api").maxAge(24*60*60)
                .httpOnly(true).build();
    }
    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, "").path("/api").build();
        return cookie;
    }
    public String getUserFromJwtToken(String token){
        return  Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


    public String generateTokenFromEmail(String email){
        Date issuerDate = new Date();
        LocalDateTime jwtLocalDate = LocalDateTime.now().plusMinutes(jwtExpirationMs);
        Date jwtMS = Date.from(jwtLocalDate.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuerDate)
                .setExpiration(jwtMS)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
