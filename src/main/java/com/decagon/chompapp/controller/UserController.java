package com.decagon.chompapp.controller;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.services.ForgotPasswordService;
import com.decagon.chompapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth/users")
public class UserController {

    private final UserService userService;
    private final ForgotPasswordService forgotPasswordService;


    @PutMapping("/edit")
    public ResponseEntity<String> editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody String email) throws MessagingException {
        return new ResponseEntity<>(forgotPasswordService.generateResetToken(email), OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@RequestBody String newPassword, @PathVariable String token) {
        return new ResponseEntity<>(forgotPasswordService.resetPassword(newPassword, token), OK);
    }
}
