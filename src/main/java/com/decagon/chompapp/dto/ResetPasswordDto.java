package com.decagon.chompapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    private String newPassword;
    private String token;
}
