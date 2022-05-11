package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.dtos.EditUserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> editUserDetails(EditUserDto editUserDto);
    ResponseEntity<String> updatePassword(PasswordDto passwordDto);

}
