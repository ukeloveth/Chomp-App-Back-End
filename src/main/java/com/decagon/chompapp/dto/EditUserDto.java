package com.decagon.chompapp.dto;

import com.decagon.chompapp.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String firstName;

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String lastName;

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
