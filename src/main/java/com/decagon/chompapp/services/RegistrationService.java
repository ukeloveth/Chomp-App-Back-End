package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.SignUpDto;
import com.decagon.chompapp.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@Service
public interface RegistrationService {
    ResponseEntity<String> registerUser(SignUpDto signUpDto, HttpServletRequest request) throws MalformedURLException;

    ResponseEntity<String> verifyRegistration(User user, HttpServletRequest request) throws MalformedURLException;

    ResponseEntity<String> verifyRegistration(String email, HttpServletRequest request) throws MalformedURLException;

    ResponseEntity<String> confirmRegistration(String token);
}
