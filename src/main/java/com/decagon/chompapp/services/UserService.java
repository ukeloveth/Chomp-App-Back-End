package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.EditUserDto;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> editUserDetails(EditUserDto editUserDto);
}
