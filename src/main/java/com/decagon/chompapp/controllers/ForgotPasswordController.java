package com.decagon.chompapp.controllers;

import com.decagon.chompapp.services.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody String email) throws MessagingException {
        return new ResponseEntity<>(forgotPasswordService.generateResetToken(email), OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@RequestBody String newPassword, @PathVariable String token) {
        return new ResponseEntity<>(forgotPasswordService.resetPassword(newPassword, token), OK);
    }
}
