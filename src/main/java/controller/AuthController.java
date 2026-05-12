package controller;

import config.TokenConfig;
import dto.response.LoginResponse;
import dto.response.RegisterUserResponse;
import dto.resquest.LoginRequest;
import dto.resquest.RegisterUserRequest;
import jakarta.validation.Valid;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.genereteToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        User novoUser = new User();
        novoUser.setPassword(passwordEncoder.encode(request.password()));
        novoUser.setEmail(request.email());
        novoUser.setName(request.name());

        userRepository.save(novoUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(novoUser.getName(), novoUser.getEmail()));
    }
}
