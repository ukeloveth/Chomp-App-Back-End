package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.dto.EmailSenderDto;
import com.decagon.chompapp.enums.Gender;
import com.decagon.chompapp.exception.UserNotFoundException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.UserRepository;
import com.decagon.chompapp.security.CustomUserDetailsService;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ResponseActions;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private EmailSenderService emailService;
    @MockBean
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("ukeloveth247@gmail.com");
        user.setPassword("great");
    }


//    @Test
//    void whenValidUserForgetsPassword_ShouldSendResetPasswordLink() throws MessagingException {
//        String jwtTokenProvider = "";
//        //User user = new User();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                user.getEmail(),
//                null,
//                Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRole())))
//        );
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername("ukeloveth247@gmail.com");
//
//        ForgotPasswordRequest forgotPasswordRequest =
//                new ForgotPasswordRequest(
//                        "ukeloveth247@gmail.com"
//                );
//        when(userRepository.findUserByEmail(forgotPasswordRequest.getEmail())).
//                thenReturn(Optional.of(user));
//        when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtToken);
//        ResponseEntity<MessageResponse> result = userService.forgotPassword(forgotPasswordRequest);
//        assertThat(result.getBody().getMessage()).isEqualTo("Kindly check email for reset Link!");
//        assertThat(forgotPasswordRequest.getEmail()).isEqualTo(user.getEmail());
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    void generateToken() throws MessagingException {
//        EmailSenderDto emailDto = new EmailSenderDto();
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(userDetailsService.loadUserByUsername(any())).thenReturn(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_PREMIUM"))));
        String token = "iuhiuhdhsjhkhaieooijowjeosdjkjskj";
        Mockito.when(jwtTokenProvider.generateToken(any())).thenReturn(token);
        Mockito.when(emailService.send(any())).thenReturn(ResponseEntity.ok("Message sent successfully"));
        String result = userService.generateResetToken(user.getEmail());
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo("Check Your Email to Reset Your Password");


    }

    /*userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User does not exits in the database"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtTokenProvider.generateToken((Authentication) userDetails);
        EmailSenderDto emailDto = new EmailSenderDto();
        emailDto.setTo(email);
        emailDto.setSubject("Reset Your Password");
        emailDto.setContent( "Please Use This Link to Reset Your Password\n" +
                "https://localhost:8081/reset-password/" + token);
        emailService.send(emailDto);
        return "Check Your Email to Reset Your Password";*/


//
//        when(userRepository.findByEmail(userDetailsService(emailService.send(new EmailSenderDto("ukeloveth247@gmail.com","great info","check this info"))))).thenReturn(Optional.of(user));
//        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);
//        ResponseEntity<MessageResponse> result = userService.forgotPassword(forgotPasswordRequest);
//        assertThat(result.getBody().getMessage()).isEqualTo("Kindly check email for reset Link!");
//        assertThat(forgotPasswordRequest.getEmail()).isEqualTo(user.getEmail());
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//          when(userRepository.findByEmail(emailService.getEmail())).thenReturn(Optional.of(user));
//                  .orElseThrow(() ->
//                    new UserNotFoundException("User does not exits in the database"));

           // UserDetails userDetails = userDetailsService.loadUserByUsername(email);

//            String token = jwtTokenProvider.generateToken((Authentication) userDetails);
//            EmailSenderDto emailDto = new EmailSenderDto();
//            emailDto.setTo(email);
//            emailDto.setSubject("Reset Your Password");
//            emailDto.setContent( "Please Use This Link to Reset Your Password\n" +
//                    "https://localhost:8081/reset-password/" + token);
//            emailService.send(emailDto);
//            return "Check Your Email to Reset Your Password";
//        }






    @Test
    void test_editUserDetails() {
        User user = User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .username("JohnnyD")
                .gender(Gender.MALE)
                .password("12345")
                .email("johnD@gmail.com")
                .build();

        EditUserDto userDto = EditUserDto.builder()
                .firstName("Johnny")
                .lastName("Daniels")
                .username("DojoMan")
                .gender(Gender.OTHERS)
                .build();

        String person = "johnD@gmail.com";
        String message = "User Details edit successful";

        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.OK);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(person);
        when(userRepository.findByUsernameOrEmail(person, person)).thenReturn(Optional.of(user));

        assertEquals(response, userService.editUserDetails(userDto));
        assertEquals(userDto.getFirstName(), user.getFirstName());

    }




}