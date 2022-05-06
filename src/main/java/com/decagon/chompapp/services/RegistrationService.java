package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.SignUpDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@Service
public interface RegistrationService {
    ResponseEntity<String> registerUser(SignUpDto signUpDto, HttpServletRequest request);
    ResponseEntity<String> confirmRegistration(String token);
}
