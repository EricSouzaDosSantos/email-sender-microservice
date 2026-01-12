package com.smh.emailsender.domain.model;

import com.smh.emailsender.domain.exception.InvalidEmailException;
import com.smh.emailsender.domain.exception.RequiredFieldException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    private final String to;
    private final String subject;
    private final String body;

    public Email(String to, String subject, String body) {
        validateRequiredFields(to, subject, body);
        validateEmailFormat(to);
        this.to = to.trim();
        this.subject = subject.trim();
        this.body = body.trim();
    }

    private void validateRequiredFields(String to, String subject, String body) {
        if (to == null || to.isBlank()) {
            throw new RequiredFieldException("to");
        }
        if (subject == null || subject.isBlank()) {
            throw new RequiredFieldException("subject");
        }
        if (body == null || body.isBlank()) {
            throw new RequiredFieldException("body");
        }
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidEmailException(String.format("Formato de e-mail inválido: %s", email));
        }
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(to, email.to) && 
               Objects.equals(subject, email.subject) && 
               Objects.equals(body, email.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, subject, body);
    }
}
