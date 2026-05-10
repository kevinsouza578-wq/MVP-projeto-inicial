package br.com.mvp.educagames.service;

import br.com.mvp.educagames.dto.AuthResponse;
import br.com.mvp.educagames.dto.LoginRequest;
import br.com.mvp.educagames.dto.RegisterRequest;
import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.exception.ConflictException;
import br.com.mvp.educagames.repository.UserRepository;
import br.com.mvp.educagames.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.username().trim();
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("Username ja esta em uso.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("E-mail ja esta em uso.");
        }

        User user = new User(
                request.name().trim(),
                username,
                email,
                passwordEncoder.encode(request.password()),
                normalizeOptional(request.photoUrl())
        );

        User savedUser = userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(savedUser), "Bearer", userService.toResponse(savedUser));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String login = request.usernameOrEmail().trim();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login,
                request.password()
        ));

        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login.toLowerCase()))
                .orElseThrow();

        return new AuthResponse(jwtService.generateToken(user), "Bearer", userService.toResponse(user));
    }

    private String normalizeOptional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
