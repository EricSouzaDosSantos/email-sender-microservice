package com.smh.emailsender.interfaces.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailRequestDTO(
        @NotBlank(message = "O campo 'to' é obrigatório")
        @Email(message = "O campo 'to' deve ser um e-mail válido")
        String to,
        
        @NotBlank(message = "O campo 'subject' é obrigatório")
        @Size(max = 200, message = "O campo 'subject' deve ter no máximo 200 caracteres")
        String subject,
        
        @NotBlank(message = "O campo 'body' é obrigatório")
        @Size(max = 5000, message = "O campo 'body' deve ter no máximo 5000 caracteres")
        String body
) {
}
