# Checkout Patterns API

API REST de checkout para uma livraria fictícia. O projeto transforma em aplicação executável os padrões estudados no laboratório DIO de Design Patterns com Java e acrescenta uma cadeia de validação extensível.

> Os pagamentos são simulados e servem apenas para demonstrar a troca de estratégias. Nenhum dado financeiro é recolhido ou enviado a um provedor.

## Padrões aplicados

- **Singleton gerenciado pelo Spring:** os serviços e registries são beans singleton por padrão. O container controla ciclo de vida e injeção; não há estado global mutável nem `getInstance()` manual.
- **Strategy:** `PaymentStrategy` seleciona PIX ou cartão sem `if/else` no fluxo de checkout. `ShippingStrategy` faz o mesmo para envio padrão ou expresso.
- **Repository:** `OrderRepository` estende `JpaRepository`, mantendo o acesso a dados fora do domínio e do controlador.
- **Facade:** `CheckoutFacade` oferece uma operação única de checkout e coordena validação, catálogo, cálculo, estratégias e persistência.
- **Chain of Responsibility (extensão):** cada `OrderRequestValidator` verifica uma regra e a cadeia pode crescer sem concentrar todas as validações num controlador.

O projeto usa injeção por construtor, DTOs para a fronteira HTTP, enums persistidos como texto e testes de integração com H2. O banco é em memória e é recriado quando a aplicação reinicia.

## Requisitos

- JDK 21
- Maven 3.9+ ou Maven Wrapper incluído (`./mvnw`)

## Executar

```bash
./mvnw spring-boot:run
```

A API inicia em `http://localhost:8080`. Para executar os testes:

```bash
./mvnw test
```

O console H2 está disponível em `http://localhost:8080/h2-console` durante a execução. Use JDBC URL `jdbc:h2:mem:checkoutdb`, utilizador `sa` e palavra-passe vazia.

## Endpoints

### Criar pedido

```bash
curl -i -X POST http://localhost:8080/api/orders \
  -H 'Content-Type: application/json' \
  -d '{
    "customerName": "Ana Silva",
    "paymentMethod": "PIX",
    "shippingMethod": "STANDARD",
    "items": [
      {"productCode": "JAVA-CLEAN-CODE", "quantity": 2},
      {"productCode": "GOF-PATTERNS", "quantity": 1}
    ]
  }'
```

A resposta inclui `201 Created`, o identificador, os itens, subtotal, frete, total, status e uma referência fictícia de pagamento. Os produtos iniciais são `JAVA-CLEAN-CODE`, `GOF-PATTERNS` e `SPRING-REST`.

Meios de pagamento aceitos: `PIX`, `CARD`. Formas de envio: `STANDARD`, `EXPRESS`.

### Consultar pedidos

```bash
curl http://localhost:8080/api/orders
curl http://localhost:8080/api/orders/1
```

## Decisões de desenho

1. O controlador conhece apenas DTOs HTTP e a fachada.
2. A fachada executa uma transação e delega cada política a uma interface de estratégia.
3. O registro de estratégias falha explicitamente se não existir implementação para um enum, em vez de aceitar um fallback silencioso.
4. A validação em cadeia verifica campos, limite de itens e existência de produtos antes de criar dados persistentes.
5. O catálogo e as tarifas de envio são exemplos locais; podem ser substituídos por adapters e configurações externas sem alterar o contrato do controller.

## Estrutura

```text
src/main/java/com/portfolio/patterns/
├── api/          # Controller, DTOs e tratamento de erros
├── application/  # Fachada de checkout
├── domain/       # Pedido, itens e enums
├── repository/   # Persistência Spring Data JPA
├── service/      # Catálogo, estratégias e cálculo
└── validation/   # Chain of Responsibility
```

## Evoluções possíveis

- persistir catálogo em banco e adicionar migrações;
- trocar o H2 por PostgreSQL e configurar perfis;
- incluir idempotência, autenticação e documentação OpenAPI;
- implementar gateway de pagamento real somente atrás de uma interface e fora do escopo deste exemplo.
