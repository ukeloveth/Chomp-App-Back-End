package com.decagon.chompapp.services;

import com.decagon.chompapp.dto.EmailSenderDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface EmailSenderService {
    ResponseEntity<String> send(EmailSenderDto emailSenderDto) throws MessagingException;
}
