package com.smh.emailsender.infrastructure.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String MDC_REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        
        MDC.put(MDC_REQUEST_ID, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);
        
        logger.info("Requisição recebida: {} {} - RequestId: {}", 
            request.getMethod(), 
            request.getRequestURI(),
            requestId
        );
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            logger.error("Erro ao processar requisição: {} {} - RequestId: {}", 
                request.getMethod(), 
                request.getRequestURI(),
                MDC.get(MDC_REQUEST_ID),
                ex
            );
        } else {
            logger.info("Requisição concluída: {} {} - Status: {} - RequestId: {}", 
                request.getMethod(), 
                request.getRequestURI(),
                response.getStatus(),
                MDC.get(MDC_REQUEST_ID)
            );
        }
        MDC.clear();
    }
}
