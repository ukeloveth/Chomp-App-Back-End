package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.EmailSenderDto;
import com.decagon.chompapp.exception.UserNotFoundException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.security.CustomUserDetailsService;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import com.decagon.chompapp.services.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final EmailSenderService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateResetToken(String email) throws MessagingException {
        userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User does not exits in the database"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtTokenProvider.generateToken((Authentication) userDetails);
        EmailSenderDto emailDto = new EmailSenderDto();
        emailDto.setTo(email);
        emailDto.setSubject("Reset Your Password");
        emailDto.setContent( "Please Use This Link to Reset Your Password\n" +
                "https://localhost:8081/reset-password/" + token);
        emailService.send(emailDto);
        return "Check Your Email to Reset Your Password";
    }

    @Override
    public String resetPassword(String newPassword, String token) {
        String email = jwtTokenProvider.getUsernameFromJwt(token);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User does not exits in the database"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password Reset Successfully";
    }
}
