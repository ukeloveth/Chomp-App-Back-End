package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.SignUpDto;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    ResponseEntity<String> registerUser(SignUpDto signUpDto);
}
