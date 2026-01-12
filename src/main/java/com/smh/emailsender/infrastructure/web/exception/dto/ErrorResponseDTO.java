package com.smh.emailsender.infrastructure.web.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String message,
        LocalDateTime timestamp
) {
}
