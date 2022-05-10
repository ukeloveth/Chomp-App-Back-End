package com.decagon.chompapp.controller;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.dto.EmailSenderDto;
import com.decagon.chompapp.dto.ResetPasswordDto;
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


    @PutMapping("/edit")
    public ResponseEntity<String> editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody EmailSenderDto emailSenderDto) throws MessagingException {
        return new ResponseEntity<>(userService.generateResetToken(emailSenderDto.getTo()), OK);
    }

    @GetMapping("/enter-password")
    public ResponseEntity<String> enterNewPassword(@RequestParam("token") String token) {
        return new ResponseEntity<>("Please enter new password", OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto, @RequestParam("token") String token) {
        return new ResponseEntity<>(userService.resetPassword(resetPasswordDto, token), OK);
    }
}
