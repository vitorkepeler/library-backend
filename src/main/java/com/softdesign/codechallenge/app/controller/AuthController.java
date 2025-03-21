package com.softdesign.codechallenge.app.controller;

import com.softdesign.codechallenge.domain.dto.LoginRequestDTO;
import com.softdesign.codechallenge.domain.dto.LoginResponseDTO;
import com.softdesign.codechallenge.domain.service.AuthenticationService;
import com.softdesign.codechallenge.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public void register(@RequestBody LoginRequestDTO loginRequestDTO) {
        authenticationService.signup(loginRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authenticationService.authenticate(loginRequestDTO);
    }
}
