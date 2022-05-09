package com.decagon.chompapp.services;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

public interface EmailSenderService {
    void send(String to, String email);
    void sendRegistrationEmail(String email, String token) throws MalformedURLException;
}
