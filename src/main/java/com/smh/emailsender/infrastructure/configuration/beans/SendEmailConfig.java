package com.smh.emailsender.infrastructure.configuration.beans;

import com.smh.emailsender.application.port.out.EmailMessagePublisherPort;
import com.smh.emailsender.application.usecase.SendEmailServiceUseCase;
import com.smh.emailsender.interfaces.mapper.EmailMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendEmailConfig {
    @Bean
    public SendEmailServiceUseCase sendEmailServiceUseCase(
            EmailMessagePublisherPort emailMessagePublisherPort,
            EmailMapper emailMapper,
            MeterRegistry meterRegistry
    ) {
        return new SendEmailServiceUseCase(emailMessagePublisherPort, emailMapper, meterRegistry);
    }
}
