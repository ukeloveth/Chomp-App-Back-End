package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.dto.ResetPasswordDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;


public interface UserService {

    ResponseEntity<String> editUserDetails(EditUserDto editUserDto);
    String generateResetToken(String email) throws MessagingException;
    String resetPassword(ResetPasswordDto resetPasswordDto, String token);

}
