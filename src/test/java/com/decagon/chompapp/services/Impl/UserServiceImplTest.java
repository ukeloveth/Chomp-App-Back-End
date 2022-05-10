package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.EditUserDto;
import com.decagon.chompapp.dto.PasswordDto;
import com.decagon.chompapp.enums.Gender;
import com.decagon.chompapp.exception.PasswordConfirmationException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.client.ResponseActions;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
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