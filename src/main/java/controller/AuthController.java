package controller;

import dto.response.LoginResponse;
import dto.response.RegisterUserResponse;
import dto.resquest.LoginRequest;
import dto.resquest.RegisterUserRequest;
import jakarta.validation.Valid;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return null;
    }

    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        User novoUser = new User();
        novoUser.setPassword(passwordEncoder.encode(request.password()));
        novoUser.setEmail(request.email());
        novoUser.setName(request.name());

        userRepository.save(novoUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(novoUser.getName(), novoUser.getEmail()));
    }
}
