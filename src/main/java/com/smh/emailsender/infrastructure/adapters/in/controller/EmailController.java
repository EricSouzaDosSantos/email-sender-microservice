package com.smh.emailsender.infrastructure.adapters.in.controller;

import com.smh.emailsender.application.usecase.SendEmailServiceUseCase;
import com.smh.emailsender.interfaces.dtos.request.EmailRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final SendEmailServiceUseCase sendEmailUseCase;

    public EmailController(SendEmailServiceUseCase sendEmailUseCase) {
        this.sendEmailUseCase = sendEmailUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody @jakarta.validation.Valid EmailRequestDTO emailRequest) {
        sendEmailUseCase.execute(emailRequest);
        return ResponseEntity.accepted().build();
    }
}
