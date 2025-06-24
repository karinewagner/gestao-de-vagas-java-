package br.com.karine.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    
    public static String objectToJson(Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objectMapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
            .withIssuer("gestao_vagas")
            .withExpiresAt(expiresIn)
            .withSubject(companyId.toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);

        return token;
    }
}
