package com.br.formigadev.pocjava.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.formigadev.pocjava.controller.dto.LoginResponse;
import com.br.formigadev.pocjava.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class TokenConfig {

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;
    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;


    public LoginResponse encodeToken(User subject){
        // tempo de exp do token
        // definir algo de criptografia
        Instant now = Instant.now();
        Instant expDateTime = now.plus(5, ChronoUnit.HOURS);

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        var token = JWT.create()
                .withIssuer("auth-server")
                .withSubject(subject.getName())
                .withIssuedAt(now) // data emissao
                .withExpiresAt(expDateTime)
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("userId", subject.getUserId().toString())
                .sign(algorithm);
        return new LoginResponse(token, expDateTime.getEpochSecond());


    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Usa a chave pública para configurar o decodificador do Spring Security.
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }
}
