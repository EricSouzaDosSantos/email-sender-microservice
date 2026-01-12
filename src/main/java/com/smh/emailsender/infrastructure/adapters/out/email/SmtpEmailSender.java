package com.smh.emailsender.infrastructure.adapters.out.email;

import com.smh.emailsender.domain.model.Email;
import com.smh.emailsender.application.port.out.EmailSenderPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSender implements EmailSenderPort {

    private final JavaMailSender javaMailSender;

    public SmtpEmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());

        javaMailSender.send(message);
    }
}
