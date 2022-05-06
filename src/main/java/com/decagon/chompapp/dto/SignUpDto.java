package com.decagon.chompapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class SignUpDto {
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String password;

}
