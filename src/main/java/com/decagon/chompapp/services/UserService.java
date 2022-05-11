package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.dtos.ResetPasswordDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;


public interface UserService {

    ResponseEntity<String> editUserDetails(EditUserDto editUserDto);
    String generateResetToken(String email) throws MessagingException;
    String resetPassword(ResetPasswordDto resetPasswordDto, String token);

    ResponseEntity<String> updatePassword(PasswordDto passwordDto);

}
