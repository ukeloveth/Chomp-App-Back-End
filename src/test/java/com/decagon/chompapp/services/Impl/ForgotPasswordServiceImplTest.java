package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.EmailSenderDto;
import com.decagon.chompapp.dto.ResetPasswordDto;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.security.CustomUserDetailsService;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import net.bytebuddy.agent.VirtualMachine;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ForgotPasswordServiceImplTest {
    @Mock
    @InjectMocks
    private ForgotPasswordServiceImpl forgotPasswordService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private EmailSenderService emailService;
    @MockBean
    private PasswordEncoder passwordEncoder;


    private User user;

    @BeforeEach
    void setUp() {
        user.getEmail();
        user.setPassword("ukeloveth247@gmail.com");
    }


//    @Test
//    void generateResetToken() {
//        when(userRepository.findByEmail("ukeloveth247@gmail.com"));
//
//        UserDetails userDetails = new UserDetails();
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//        String token = jwtTokenProvider.generateToken((Authentication) userDetails);
//        EmailSenderDto emailDto = new EmailSenderDto();
//        emailDto.setTo(email);
//        emailDto.setSubject("Reset Your Password");
//        emailDto.setContent( "Please Use This Link to Reset Your Password\n" +
//                "https://localhost:8081/reset-password/" + token);
//        emailService.send(emailDto);

    }



//    @Test
//    void resetPassword(){
//
//        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
//        Mockito.when(forgotPasswordService).thenReturn(new ResponseEntity<>("Use this Link to reset your password",HttpStatus.OK));
//    Mockito.when(resetPasswordDto.getNewPassword()).thenReturn(forgotPasswordService.resetPassword("123",""));
//        UserRepository userRepository = new UserRepository();
//    }

//}
