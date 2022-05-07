package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.UserDto;
import com.decagon.chompapp.exceptions.UserNotFoundException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> editUserDetails(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        if (userDto.getFirstName() != null)
            user.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null)
            user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());

        userRepository.save(user);

        String message = "User Details edit successful";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
