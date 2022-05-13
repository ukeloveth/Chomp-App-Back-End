package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.dtos.ResetPasswordDto;

import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.enums.Gender;
import com.decagon.chompapp.exceptions.PasswordConfirmationException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.security.CustomUserDetailsService;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import java.util.Collections;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private EmailSenderService emailService;


    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("ukeloveth247@gmail.com");
        user.setPassword("great");

    }

    @Test
    void generateToken() throws MessagingException {
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(userDetailsService.loadUserByUsername(any())).thenReturn(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_PREMIUM"))));
        String token = "iuhiuhdhsjhkhaieooijowjeosdjkjskj";
        Mockito.when(jwtTokenProvider.generateToken(any())).thenReturn(token);
        Mockito.when(emailService.send(any())).thenReturn(ResponseEntity.ok("Message sent successfully"));
        String result = userService.generateResetToken(user.getEmail());
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo("Check Your Email to Reset Your Password");


    }

    @Test
    void resetPassword(){
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto("12345", "12345");
        Mockito.when(jwtTokenProvider.getUsernameFromJwt(any())).thenReturn(user.getEmail());
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        String newPassword = "iou23iu23ioy3o73873ii";
        Mockito.when(passwordEncoder.encode(any())).thenReturn(newPassword);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        String resetPassword = userService.resetPassword(resetPasswordDto, "hsdjksuiwue");
        org.assertj.core.api.Assertions.assertThat(resetPassword).isEqualTo("Password Reset Successfully");
    }

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

    @Test
    void testUpdatePassword() {

        PasswordDto passwordDto = mock(PasswordDto.class);
        User user = User.builder()
                .userId(1L)
                .firstName("adekunle")
                .lastName("adegoke")
                .username("kay")
                .email("adekunle@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .build();

        when(passwordDto.getConfirmPassword()).thenReturn("1234");
        when(passwordDto.getNewPassword()).thenReturn("kay");
        assertThrows(PasswordConfirmationException.class, () -> this.userService.updatePassword(passwordDto));
        verify(passwordDto).getConfirmPassword();
        verify(passwordDto).getNewPassword();
        assertFalse(passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword()));
    }




}