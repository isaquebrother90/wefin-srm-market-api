
## Wefin SRM Market - API de Conversão de Moedas

## 1. Propósito da API

Esta API foi desenvolvida como solução para o desafio técnico do "Mercado de Pulgas dos Mil Saberes" do reino de Wefin. O seu principal objetivo é fornecer um sistema robusto e flexível para a conversão de moedas entre o **Ouro Real (GOL)**, utilizado pelos mercadores locais, e o **Tibar (TIB)**, utilizado pelos comerciantes anãos.

A solução permite a consulta de taxas de câmbio, a realização de trocas em tempo real e a aplicação de regras de conversão personalizadas para diferentes tipos de produtos, garantindo que as negociações no mercado de Wefin ocorram de forma eficiente e segura.

---

## 2. Diagrama de Entidade-Relacionamento (DER)

O diagrama a seguir representa a arquitetura do banco de dados projetada para a solução. Ele inclui as tabelas para moedas, produtos, taxas de câmbio e o registro de transações, com seus respectivos relacionamentos e cardinalidades.

```mermaid
    USERS {
        bigint id PK
        varchar username UK
        varchar password
        varchar role
    }

    CURRENCY {
        bigint id PK
        varchar name UK
        varchar code UK
    }

    PRODUCT {
        bigint id PK
        varchar name
        varchar originRealm
    }

    EXCHANGE_RATE {
        bigint id PK
        bigint from_currency_id FK
        bigint to_currency_id FK
        decimal rate
        date effective_date
    }

    TRANSACTION {
        bigint id PK
        bigint product_id FK
        decimal original_amount
        decimal converted_amount
        bigint original_currency_id FK
        bigint destination_currency_id FK
        timestamp transaction_date
    }

    USERS ||--o{ EXCHANGE_RATE : "pode registrar"
    CURRENCY ||--o{ EXCHANGE_RATE : "é moeda de origem"
    CURRENCY ||--o{ EXCHANGE_RATE : "é moeda de destino"
    CURRENCY ||--o{ TRANSACTION : "é moeda de origem"
    CURRENCY ||--o{ TRANSACTION : "é moeda de destino"
    PRODUCT ||--o{ TRANSACTION : "é negociado em"
```

---

## 3. Como Executar a Aplicação

Existem duas maneiras de executar a aplicação, sendo a opção com Docker a mais recomendada por sua simplicidade.

### Opção 1: Com Docker (Recomendado)

Esta é a forma mais fácil de executar o projeto, pois não requer a instalação de Java ou Maven na sua máquina.

1.  **Pré-requisitos:**
    *   [Docker](https://www.docker.com/get-started) e Docker Compose instalados.

2.  **Passos:**
    *   Clone o repositório e navegue até a pasta raiz do projeto.
    *   Execute o seguinte comando no terminal:
        ```bash
        docker-compose up --build
        ```
    *   O Docker irá compilar o projeto, criar a imagem e iniciar o container. A API estará pronta para uso quando os logs indicarem que a aplicação Spring Boot foi iniciada.

### Opção 2: Localmente com Maven

1.  **Pré-requisitos:**
    *   JDK 17 (ou superior) instalado.
    *   Apache Maven instalado.

2.  **Passos:**
    *   Clone o repositório e navegue até a pasta raiz do projeto.
    *   Execute o seguinte comando no terminal para compilar e empacotar a aplicação:
        ```bash
        mvn clean install
        ```
    *   Após a build bem-sucedida, execute o arquivo `.jar` gerado:
        ```bash
        java -jar target/wefin-srm-market-api-0.0.1-SNAPSHOT.jar
        ```

---

## 4. Acessando a API e a Documentação

Independentemente do método de execução, a aplicação estará disponível nos seguintes endereços:

*   **URL Base da API:** `http://localhost:8080`
*   **Documentação Interativa (Swagger UI):** `http://localhost:8080/swagger-ui.html`
*   **Console do Banco de Dados H2:** `http://localhost:8080/h2-console`
    *   **JDBC URL:** `jdbc:h2:mem:wefindb`
    *   **Username:** `sa`
    *   **Password:** `password`

---

## 5. Endpoints da API

A seguir, a descrição dos endpoints disponíveis.

### **Taxas de Câmbio (`/api/v1/rates`)**

*   **`POST /api/v1/rates`**
    *   **Propósito:** Cria e persiste uma nova taxa de câmbio. Esta operação é protegida e requer autenticação de administrador.
    *   **Autenticação:** `ADMIN`. Para usar no Swagger, clique no botão "Authorize" e use as credenciais `admin` / `password`.
    *   **Corpo da Requisição:** `ExchangeRateUpdateDto` (JSON com `fromCurrencyCode`, `toCurrencyCode` e `rate`).
    *   **Resposta:** `200 OK` com o `ExchangeRateDto` da taxa criada.

*   **`GET /api/v1/rates`**
    *   **Propósito:** Consulta a taxa de câmbio mais recente e válida entre duas moedas.
    *   **Autenticação:** Nenhuma (público).
    *   **Parâmetros da Query:** `from` (código da moeda de origem), `to` (código da moeda de destino).
    *   **Resposta:** `200 OK` com o `ExchangeRateDto` da taxa encontrada.

### **Transações (`/api/v1/transactions`)**

*   **`POST /api/v1/transactions/exchange`**
    *   **Propósito:** Realiza uma conversão de moeda para um produto específico, aplicando as regras de negócio (como taxas e sobretaxas), e persiste um registro da transação no banco de dados.
    *   **Autenticação:** Nenhuma (público).
    *   **Corpo da Requisição:** `ConversionRequestDto` (JSON com `productId`, `amount`, `fromCurrencyCode`, `toCurrencyCode`).
    *   **Resposta:** `200 OK` com o `TransactionResponseDto`, contendo todos os detalhes da transação realizada.

---

## 6. Arquitetura e Decisões Técnicas

### Principais Ferramentas e Tecnologias

*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3.2.2
*   **Acesso a Dados:** Spring Data JPA com Hibernate
*   **Banco de Dados:** H2 (em memória, para facilitar a avaliação)
*   **Segurança:** Spring Security (autenticação HTTP Basic)
*   **Documentação da API:** SpringDoc (OpenAPI 3.0 / Swagger UI)
*   **Build:** Apache Maven
*   **Containerização:** Docker e Docker Compose

### Controle Transacional

Para garantir a **atomicidade, consistência e a capacidade de rollback**, a aplicação utiliza a gestão de transações declarativas do Spring. A anotação `@Transactional` foi aplicada na camada de serviço (`@Service`). Isso garante que qualquer operação de negócio, como a `executeExchange`, seja executada como uma unidade "tudo ou nada": ou todas as operações no banco de dados são concluídas com sucesso (commit), ou, em caso de qualquer erro (`RuntimeException`), todas são desfeitas (rollback), mantendo a integridade dos dados.

### Cobertura de Testes

O projeto foi desenvolvido com um forte foco em qualidade, resultando em uma **cobertura de testes superior a 90%**, validada com a ferramenta **JaCoCo**. A suíte de testes inclui:
*   **Testes de Unidade Puros:** Para validar a lógica de negócio isolada (ex: as estratégias de conversão).
*   **Testes de Unidade com Mocks (Mockito):** Para testar a lógica de serviços e controllers, mockando suas dependências.
*   **Testes de Integração (`@SpringBootTest`):** Para validar o fluxo completo da aplicação, desde a requisição HTTP até a interação com o banco de dados.

---

## 7. Script SQL (Schema)

O script a seguir define a estrutura das tabelas, relacionamentos e restrições do banco de dados. Ele é executado automaticamente pelo Hibernate na inicialização da aplicação.

```sql
-- Script de Criação de Tabelas

CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(3) NOT NULL UNIQUE
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    origin_realm VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE exchange_rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_currency_id BIGINT NOT NULL,
    to_currency_id BIGINT NOT NULL,
    rate DECIMAL(19, 4) NOT NULL,
    effective_date DATE NOT NULL,
    FOREIGN KEY (from_currency_id) REFERENCES currency(id),
    FOREIGN KEY (to_currency_id) REFERENCES currency(id)
);

CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    original_amount DECIMAL(19, 4) NOT NULL,
    converted_amount DECIMAL(19, 4) NOT NULL,
    original_currency_id BIGINT NOT NULL,
    destination_currency_id BIGINT NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (original_currency_id) REFERENCES currency(id),
    FOREIGN KEY (destination_currency_id) REFERENCES currency(id)
);
```

---

## 8. Próximas Melhorias

Este projeto representa uma base sólida. Para evoluir para um ambiente de produção real, as seguintes melhorias poderiam ser implementadas:

*   **CI/CD:** Configurar um pipeline de Integração Contínua e Entrega Contínua (usando ferramentas como GitHub Actions, Jenkins ou GitLab CI) para automatizar a execução de testes, a verificação de cobertura (JaCoCo) e o deploy da aplicação.
*   **Banco de Dados de Produção:** Migrar do H2 em memória para um banco de dados mais robusto, como PostgreSQL ou MySQL, utilizando Flyway ou Liquibase para gerenciar as migrações de schema.
*   **Logs e auditoria:** Implementar o uso de libs como Slf4j, Logback, para registro e padronização de eventos.
*   **Observabilidade:** Implementar um stack de monitoramento com Prometheus (para métricas), Grafana (para dashboards) e um sistema de logging centralizado (como o stack ELK), utilizando o Spring Boot Actuator como base.
*   **Segurança Avançada:** Substituir a autenticação HTTP Basic por um fluxo mais seguro e flexível, como OAuth2/JWT, permitindo a integração com provedores de identidade.
*   **Cache:** Implementar uma camada de cache (com Redis ou Caffeine) para as taxas de câmbio, que não mudam com frequência, a fim de melhorar a performance e reduzir a carga no banco de dados.
```