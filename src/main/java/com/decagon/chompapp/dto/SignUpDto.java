package com.decagon.chompapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SignUpDto {
        private String firstName;
        private String lastName;
        private String username;

        @NotNull
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid",
                regexp = "[A-Za-z0-9][A-Za-z0-9!.#$%&'*+=?^_{|}~-]{1,50}@[A_Za-z]+[.][A-Z.a-z]{2,6}"
        )
        private String email;
        private String password;
}
