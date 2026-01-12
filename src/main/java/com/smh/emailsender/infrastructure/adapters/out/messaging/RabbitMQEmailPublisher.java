package com.smh.emailsender.infrastructure.adapters.out.messaging;

import com.smh.emailsender.application.port.out.EmailMessagePublisherPort;
import com.smh.emailsender.domain.model.Email;
import com.smh.emailsender.infrastructure.adapters.out.messaging.dto.EmailMessageDTO;
import com.smh.emailsender.infrastructure.configuration.messager.RabbitMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEmailPublisher implements EmailMessagePublisherPort {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQEmailPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEmailPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishEmailMessage(Email email) {
        try {
            EmailMessageDTO message = new EmailMessageDTO(
                email.getTo(),
                email.getSubject(),
                email.getBody()
            );
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.EMAIL_QUEUE, message);
            logger.info("Mensagem de e-mail publicada na fila para: {}", email.getTo());
        } catch (Exception e) {
            logger.error("Erro ao publicar mensagem na fila para: {}", email.getTo(), e);
            throw new RuntimeException("Falha ao publicar mensagem na fila", e);
        }
    }
}
