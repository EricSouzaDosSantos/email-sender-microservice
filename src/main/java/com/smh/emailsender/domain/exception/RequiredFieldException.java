package com.smh.emailsender.domain.exception;

public class RequiredFieldException extends DomainException {
    public RequiredFieldException(String fieldName) {
        super(String.format("Campo obrigatório não informado: %s", fieldName));
    }
}
