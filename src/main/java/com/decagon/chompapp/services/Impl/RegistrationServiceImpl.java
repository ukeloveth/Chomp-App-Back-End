package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.SignUpDto;
import com.decagon.chompapp.models.Role;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.RoleRepository;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Override
    public ResponseEntity<String> registerUser(SignUpDto signUpDto) {
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_PREMIUM").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK );
    }
}
