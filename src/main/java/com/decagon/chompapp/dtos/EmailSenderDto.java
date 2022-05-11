package com.decagon.chompapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailSenderDto {
    private String to;
    private String subject;
    private String content;
}
