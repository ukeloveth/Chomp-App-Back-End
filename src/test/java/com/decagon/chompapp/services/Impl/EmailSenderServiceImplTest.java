package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dto.EmailSenderDto;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import static org.mockito.ArgumentMatchers.any;


import javax.mail.internet.MimeMessage;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmailSenderServiceImplTest {

    @Mock
    EmailSenderServiceImpl emailSenderService;

    @MockBean
    MimeMessage mimeMessage;

    @MockBean
    JavaMailSender mailSender;

    @MockBean
    MimeMessageHelper helper;

    @Test
    void send() {
        EmailSenderDto emailSenderDto = new EmailSenderDto("okoyedennis7@gmail.com", "Test Mail", "This is a test Mail");
        Mockito.when(emailSenderService.send(any())).thenReturn(new ResponseEntity<>("Message sent successfully", HttpStatus.OK));

        Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        ResponseEntity<String> responseEntity = emailSenderService.send(emailSenderDto);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Message sent successfully");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}