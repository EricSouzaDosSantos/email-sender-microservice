package com.smh.emailsender.infrastructure.adapters.in.messaging;

import com.smh.emailsender.application.port.out.EmailSenderPort;
import com.smh.emailsender.domain.model.Email;
import com.smh.emailsender.infrastructure.adapters.out.messaging.dto.EmailMessageDTO;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailMessageListener.class);
    private final EmailSenderPort emailSenderPort;
    private final Counter emailProcessedCounter;
    private final Counter emailErrorCounter;

    public EmailMessageListener(
            EmailSenderPort emailSenderPort,
            MeterRegistry meterRegistry
    ) {
        this.emailSenderPort = emailSenderPort;
        this.emailProcessedCounter = Counter.builder("email.processed")
                .description("Total de e-mails processados com sucesso")
                .register(meterRegistry);
        this.emailErrorCounter = Counter.builder("email.errors")
                .description("Total de erros ao processar e-mails")
                .register(meterRegistry);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name:email.queue}")
    public void processEmailMessage(EmailMessageDTO message) {
        try {
            logger.info("Processando mensagem de e-mail para: {}", message.to());
            Email email = new Email(message.to(), message.subject(), message.body());
            emailSenderPort.send(email);
            emailProcessedCounter.increment();
            logger.info("E-mail enviado com sucesso para: {}", message.to());
        } catch (Exception e) {
            emailErrorCounter.increment();
            logger.error("Erro ao processar e-mail para: {}", message.to(), e);
            throw e;
        }
    }
}
