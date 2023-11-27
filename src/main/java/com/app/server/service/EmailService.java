package com.app.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String email, String token) {
        String validationLink = "http://localhost:8080/api/v1/users/validateResetToken?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tomasmoscarelli@hotmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + validationLink);

        mailSender.send(message);
    }
}
