package com.smh.emailsender.infrastructure.web.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class EmailMetrics {

    private final Counter emailRequestCounter;
    private final Timer emailRequestTimer;

    public EmailMetrics(MeterRegistry meterRegistry) {
        this.emailRequestCounter = Counter.builder("email.requests")
                .description("Total de requisições de envio de e-mail")
                .tag("status", "received")
                .register(meterRegistry);
        
        this.emailRequestTimer = Timer.builder("email.request.duration")
                .description("Tempo de processamento de requisições de e-mail")
                .register(meterRegistry);
    }

    public void incrementEmailRequest() {
        emailRequestCounter.increment();
    }

    public Timer.Sample startTimer() {
        return Timer.start();
    }

    public void recordTimer(Timer.Sample sample) {
        sample.stop(emailRequestTimer);
    }
}
