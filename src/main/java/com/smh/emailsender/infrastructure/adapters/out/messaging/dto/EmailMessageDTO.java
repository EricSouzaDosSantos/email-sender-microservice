package com.smh.emailsender.infrastructure.adapters.out.messaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmailMessageDTO(
        @JsonProperty("to") String to,
        @JsonProperty("subject") String subject,
        @JsonProperty("body") String body
) {
    @JsonCreator
    public EmailMessageDTO {
    }
}
