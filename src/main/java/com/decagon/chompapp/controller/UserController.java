package com.decagon.chompapp.controller;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.dto.PasswordDto;
import com.decagon.chompapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth/users")
public class UserController {

    private final UserService userService;


    @PutMapping("/edit")
    public ResponseEntity<String> editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }
    @PutMapping("/password-update")
    public ResponseEntity<String> login(@RequestBody PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }
}
