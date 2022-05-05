package com.decagon.chompapp.controller;

import com.decagon.chompapp.dto.SignUpDto;
import com.decagon.chompapp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/signUp")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
        return registrationService.registerUser(signUpDto);
    }
}
