package com.smh.emailsender.application.port.out;

import com.smh.emailsender.domain.model.Email;

public interface EmailSenderPort {
    void send(Email email);
}
