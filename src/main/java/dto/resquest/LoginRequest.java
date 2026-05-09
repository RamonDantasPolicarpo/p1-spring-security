package dto.resquest;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "E-mail vazio") String email,
                           @NotEmpty(message = "Senha vazia") String password) {
}
