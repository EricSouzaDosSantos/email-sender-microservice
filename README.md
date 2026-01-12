# Email Sender App

Aplicação de envio de e-mails desenvolvida com arquitetura limpa (Clean Architecture), utilizando Spring Boot, RabbitMQ para processamento assíncrono e observabilidade completa.

##  Arquitetura

A aplicação segue os princípios da **Arquitetura Limpa**, com separação clara de responsabilidades:

- **Domain**: Entidades e regras de negócio
- **Application**: Casos de uso e portas (interfaces)
- **Infrastructure**: Implementações concretas (adapters)
- **Interfaces**: DTOs e contratos de API

## Funcionalidades

- Validações no domínio (e-mail inválido, campos obrigatórios)
- Tratamento de exceções global
- Envio assíncrono com fila (RabbitMQ)
- Observabilidade (logs estruturados, métricas, Actuator)
- Dockerização completa

## Pré-requisitos

- Java 17+
- Maven 3.9+
- Docker e Docker Compose (para execução com containers)
- RabbitMQ (ou usar via Docker Compose)

## Configuração

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente para o envio de e-mails:

```bash
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu-email@gmail.com
MAIL_PASSWORD=sua-senha-app
```

### Executando Localmente

1. Inicie o RabbitMQ:
```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management-alpine
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

### Executando com Docker Compose

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`

## 📡 Endpoints

### Enviar E-mail

```http
POST /emails
Content-Type: application/json

{
  "to": "destinatario@example.com",
  "subject": "Assunto do e-mail",
  "body": "Corpo do e-mail"
}
```

### Health Check

```http
GET /actuator/health
```

### Métricas Prometheus

```http
GET /actuator/prometheus
```

## Observabilidade

### Logs

Os logs são estruturados e incluem:
- Request ID para rastreamento
- Timestamp formatado
- Nível de log
- Contexto da requisição

### Métricas

A aplicação expõe as seguintes métricas:
- `email.requests`: Total de requisições recebidas
- `email.processed`: Total de e-mails processados com sucesso
- `email.errors`: Total de erros ao processar e-mails
- `email.request.duration`: Tempo de processamento das requisições

### Actuator

Endpoints disponíveis:
- `/actuator/health`: Status de saúde da aplicação
- `/actuator/info`: Informações da aplicação
- `/actuator/metrics`: Lista de métricas disponíveis
- `/actuator/prometheus`: Métricas no formato Prometheus

## Docker

### Build da Imagem

```bash
docker build -t email-sender-app .
```

### Executar Container

```bash
docker run -p 8080:8080 \
  -e RABBITMQ_HOST=localhost \
  -e MAIL_USERNAME=seu-email@gmail.com \
  -e MAIL_PASSWORD=sua-senha \
  email-sender-app
```

## Testes

```bash
mvn test
```

## Estrutura do Projeto

```
src/main/java/com/smh/emailsender/
├── application/
│   ├── port/
│   │   ├── in/          # Portas de entrada (interfaces de casos de uso)
│   │   └── out/          # Portas de saída (interfaces de serviços externos)
│   └── usecase/         # Implementações dos casos de uso
├── domain/
│   ├── model/           # Entidades de domínio
│   └── exception/       # Exceções de domínio
├── infrastructure/
│   ├── adapters/
│   │   ├── in/          # Adaptadores de entrada (controllers, listeners)
│   │   └── out/         # Adaptadores de saída (repositórios, serviços externos)
│   ├── configuration/   # Configurações do Spring
│   └── web/             # Componentes web (interceptors, métricas)
└── interfaces/
    └── dtos/            # Data Transfer Objects
```

## Validações

A aplicação valida:
- Formato de e-mail (regex)
- Campos obrigatórios (to, subject, body)
- Tamanho máximo dos campos (subject: 200, body: 5000)

## Tecnologias

- Spring Boot 4.0.1
- Spring AMQP (RabbitMQ)
- Spring Boot Actuator
- Micrometer (métricas)
- Jakarta Validation
- SLF4J + Logback (logs)
