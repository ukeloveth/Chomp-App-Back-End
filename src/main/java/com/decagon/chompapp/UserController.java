package com.decagon.chompapp;

import com.decagon.chompapp.dto.UserDto;
import com.decagon.chompapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;


    @PutMapping("/edit")
    public ResponseEntity<String> editUserDetails(@RequestBody UserDto userDto) {
        return userService.editUserDetails(userDto);
    }
}
