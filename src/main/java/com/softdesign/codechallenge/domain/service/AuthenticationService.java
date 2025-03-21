package com.softdesign.codechallenge.domain.service;

import com.softdesign.codechallenge.dataaccess.entity.UserEntity;
import com.softdesign.codechallenge.dataaccess.repository.UserRepository;
import com.softdesign.codechallenge.domain.dto.LoginRequestDTO;
import com.softdesign.codechallenge.domain.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void signup(LoginRequestDTO loginRequestDTO) {
        UserEntity user = UserEntity.builder()
                .name("teste")
                .email(loginRequestDTO.email())
                .password(passwordEncoder.encode(loginRequestDTO.password()))
                .build();

        userRepository.save(user);
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                )
        );

        UserEntity authenticatedUser = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return LoginResponseDTO.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
    }
}
