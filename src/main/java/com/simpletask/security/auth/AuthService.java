package com.simpletask.security.auth;

import com.simpletask.security.config.JwtService;
import com.simpletask.security.user.Role;
import com.simpletask.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import com.simpletask.security.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .registrationDate(LocalDateTime.now())
                .role(Role.USER)
                .build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserExistsException();
        }
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token("Bearer " + jwt)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token("Bearer " + jwt)
                .build();
    }
}
