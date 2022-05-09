package com.decagon.chompapp.controller;


import com.decagon.chompapp.dto.JwtAuthResponse;
import com.decagon.chompapp.dto.LoginDto;
import com.decagon.chompapp.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) throws Exception {

        return loginService.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(String token) {

        return loginService.logout(token);
    }
}
