package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.JwtAuthResponse;
import com.decagon.chompapp.dto.LoginDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    ResponseEntity<JwtAuthResponse> login(LoginDto loginDto) throws Exception;
    ResponseEntity<?> logout(String token);


}
