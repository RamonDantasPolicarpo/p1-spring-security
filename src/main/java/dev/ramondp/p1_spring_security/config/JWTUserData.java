package dev.ramondp.p1_spring_security.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
