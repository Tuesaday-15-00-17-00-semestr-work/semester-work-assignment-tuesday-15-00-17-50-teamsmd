package lib.smd.SMDLIB.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JTWGenerator {
	
	private SecretKey JWTSecret = Keys.hmacShaKeyFor("verysecret".getBytes(StandardCharsets.UTF_8));;
	
	public String generateToken(Authentication auth) {
		String username = auth.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + 9999999);
		System.out.println("test");
		String token = Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expireDate)
				.signWith(JWTSecret)
				.compact();
		System.out.println("preslo");
		return token;
	}
	
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(JWTSecret)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(JWTSecret).build().parseSignedClaims(token);
			return true;
		}catch(Exception e) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
	}
}
