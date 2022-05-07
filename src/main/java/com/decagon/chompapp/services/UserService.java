package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> editUserDetails(Long userId, UserDto userDto);
}
