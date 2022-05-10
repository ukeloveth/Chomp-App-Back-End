package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> editUserDetails(EditUserDto editUserDto) {
        String person = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameOrEmail(person, person).get();
        if (editUserDto.getFirstName() != null)
            user.setFirstName(editUserDto.getFirstName());
        if (editUserDto.getLastName() != null)
            user.setLastName(editUserDto.getLastName());
        if (editUserDto.getGender() != null)
            user.setGender(editUserDto.getGender());

        userRepository.save(user);

        String message = "User Details edit successful";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
