package com.powerup.user_microservice.infrastructure.configuration.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class JwtToken {

    @Value("${security.jwt.secret}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGeneratedToken;

    @Value("${security.jwt.expiration}")
    private String expirationTime;

    public String generateToken(Authentication authentication) {

        long expirationTimeLong = Long.parseLong(this.expirationTime);

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found"))
                .getAuthority();

        return JWT.create()
                .withIssuer(this.userGeneratedToken)
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + expirationTimeLong))
                .withJWTId(java.util.UUID.randomUUID().toString())
                .withNotBefore(new java.util.Date(System.currentTimeMillis()))
                .sign(algorithm);

    }

    public DecodedJWT validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            return JWT.require(algorithm)
                    .withIssuer(this.userGeneratedToken)
                    .build()
                    .verify(token);

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token");
        }

    }

    public String getUserName(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claim) {
        return decodedJWT.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
