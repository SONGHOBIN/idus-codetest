package com.common.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {
    private static final String AUTHORITIES_KEY = "auth";

    private static final String secret = "anNoLXNwcmluZ2Jvb3QtYW5kLWp3dC10dXRvcmlhbC10aGlzLWlzLWZvci1nZW5lcmF0aW5nLWp3dC1zZWNyZXRrZXktYmFzZTY0Cg==";
    private static final long tokenValidityInMilliseconds = 86400 * 1000;

    private static byte[] keyBytes = Decoders.BASE64.decode(secret);
    private static Key key = Keys.hmacShaKeyFor(keyBytes);

    public static String createToken(Authentication authentication) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);

        return Jwts.builder()
        		.setHeader(headers)
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public static Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // claims과 authorities 정보를 활용해 User (org.springframework.security.core.userdetails.User) 객체 생성
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            
            return true;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        
        return false;
    }
    
	public static String getUserEmail(String jwt) throws RuntimeException {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt.substring("Bearer ".length())).getBody().getSubject();
	}
}
