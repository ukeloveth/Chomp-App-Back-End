package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.SignUpDto;
import com.decagon.chompapp.models.Role;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.RoleRepository;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.services.EmailSenderService;
import com.decagon.chompapp.services.RegistrationService;
import com.decagon.chompapp.utils.Utility;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private EmailSenderService emailSender;


    @Override
    public ResponseEntity<String> registerUser(SignUpDto signUpDto, HttpServletRequest request) {
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setUsername(signUpDto.getUsername());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Optional<Role> roles = roleRepository.findByName("ROLE_PREMIUM");
        user.setRoles(Collections.singleton(roles.get()));
        String token = RandomString.make(30);
        user.setConfirmationToken(token);
        user.setIsEnabled(false);
        userRepository.save(user);

        String  confirmRegistrationLink = Utility.getSiteURL(request) + "/confirmRegistration?token=" + token;

        String content = "<p>Hello,</p>"
                + "<p>Congrats!!! Your registration was successful.</p>"
                + "<p>Click the link below to verify your account:</p>"
                + "<p><a href=\"" + confirmRegistrationLink + "\">Verify my account</a></p>"
                + "<br>"
                + "<p>Ignore this email if you did not register, "
                + "or you have not made the request.</p>";
        emailSender.send(signUpDto.getEmail(), content);

        return new ResponseEntity<>("User registered successfully. Kindly check your mail inbox or junk folder to verify your account", HttpStatus.OK );
    }

    @Override
    public ResponseEntity<String> confirmRegistration(String token) {
        Optional<User> existingUser = userRepository.findByConfirmationToken(token);
        existingUser.orElseThrow().setConfirmationToken(null);
        existingUser.orElseThrow().setIsEnabled(true);
        userRepository.save(existingUser.orElseThrow());
        return new ResponseEntity<>("Account verification successful", HttpStatus.ACCEPTED);
    }
}
