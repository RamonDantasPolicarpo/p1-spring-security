package dev.ramondp.p1_spring_security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ramondp.p1_spring_security.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;


@Component
public class TokenConfig {

    Algorithm algorithm = Algorithm.HMAC256("secret");

    private String secret = "senha-super-hiper-mega-secreta";

    public String genereteToken(User use) {
        return JWT.create()
                .withClaim("userId", use.getId())
                .withClaim("email", use.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");

            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build());
        }
        catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
