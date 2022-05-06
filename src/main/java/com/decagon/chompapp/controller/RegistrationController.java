package com.decagon.chompapp.controller;

import com.decagon.chompapp.dto.SignUpDto;
import com.decagon.chompapp.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
public class RegistrationController {
//    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto, HttpServletRequest request){
        return registrationService.registerUser(signUpDto,request);
    }

    @PostMapping("/confirmRegistration")
    public ResponseEntity<String> confirmRegistration(@RequestParam(value = "token") String token) {
        return registrationService.confirmRegistration(token);

    }
}
