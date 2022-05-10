package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.enums.Gender;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.security.CustomUserDetailsService;
import com.decagon.chompapp.security.JwtAuthenticationEntryPoint;
import com.decagon.chompapp.security.JwtAuthenticationFilter;
import com.decagon.chompapp.services.Impl.UserServiceImpl;
import com.decagon.chompapp.services.ProductServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
class UserControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductServices productServices;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    UserRepository userRepository;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);


    @Test
    void editUserDetails() throws Exception {

        String message = "User Details edit successful";

        String person = "johnD@gmail.com";

        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.OK);

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
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(person);
        when(userRepository.findByUsernameOrEmail(person, person)).thenReturn(Optional.of(user));

        when(userService.editUserDetails(userDto)).thenReturn(response);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/auth/users/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}