package com.maverix.makeatable.config.Mail;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailSender {


    private JavaMailSender javaMailSender;

    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("erfanhabeebvn@gmail.com");
        message.setSubject("Hello");
        message.setText("Hello, this is a test email.");
        javaMailSender.send(message);
    }
}
