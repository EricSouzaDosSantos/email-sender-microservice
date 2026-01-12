package com.smh.emailsender.application.usecase;

import com.smh.emailsender.application.port.out.EmailMessagePublisherPort;
import com.smh.emailsender.domain.model.Email;
import com.smh.emailsender.interfaces.dtos.request.EmailRequestDTO;
import com.smh.emailsender.interfaces.mapper.EmailMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailServiceUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SendEmailServiceUseCase.class);
    private final EmailMessagePublisherPort emailMessagePublisherPort;
    private final EmailMapper emailMapper;
    private final Counter emailRequestCounter;
    private final Timer emailRequestTimer;

    public SendEmailServiceUseCase(
            EmailMessagePublisherPort emailMessagePublisherPort,
            EmailMapper emailMapper,
            MeterRegistry meterRegistry
    ) {
        this.emailMessagePublisherPort = emailMessagePublisherPort;
        this.emailMapper = emailMapper;
        this.emailRequestCounter = Counter.builder("email.requests")
                .description("Total de requisições de envio de e-mail")
                .tag("status", "received")
                .register(meterRegistry);
        this.emailRequestTimer = Timer.builder("email.request.duration")
                .description("Tempo de processamento de requisições de e-mail")
                .register(meterRegistry);
    }

    public void execute(EmailRequestDTO emailRequest) {
        logger.info("Processando requisição para enviar e-mail para: {}", emailRequest.to());
        emailRequestCounter.increment();
        
        Timer.Sample sample = Timer.start();
        try {
            Email email = emailMapper.toDomain(emailRequest);
            logger.info("E-mail mapeado com sucesso, enfileirando para envio assíncrono: {}", email.getTo());
            emailMessagePublisherPort.publishEmailMessage(email);
            logger.info("E-mail enfileirado com sucesso para: {}", email.getTo());
        } finally {
            sample.stop(emailRequestTimer);
        }
    }
}
