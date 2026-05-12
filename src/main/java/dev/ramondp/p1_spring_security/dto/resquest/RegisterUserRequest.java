package dev.ramondp.p1_spring_security.dto.resquest;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "Nome não deve ser vazio")String name,
                                  @NotEmpty(message = "E-mail não deve ser vazio") String email,
                                  @NotEmpty(message = "Senha não deve ser vazia") String password) {
}
