# Projeto Final — Design Patterns com Java

Projeto de portfólio inspirado no laboratório DIO **“Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework”**. Em vez de incluir somente a API nova, esta entrega organiza numa raiz Maven os exemplos práticos em Java puro e um projeto evoluído com Spring.

## O que está incluído

1. **`java-puro/` — exemplos de aula reproduzidos e organizados:**
   - Singleton: eager, lazy thread-safe e lazy holder;
   - Strategy: robô que troca comportamento normal, defensivo e agressivo;
   - Facade: migração de cliente que coordena CRM e consulta local de CEP.
2. **`checkout-patterns-api/` — projeto final novo com Spring Boot:**
   - Singleton gerenciado pelo container;
   - Strategy para pagamento simulado e frete;
   - Repository com Spring Data JPA;
   - Facade para o fluxo de checkout;
   - Chain of Responsibility como extensão para validar pedidos.
3. **`docs/ANALISE-DETALHADA-CURSO.md` — análise aula a aula, referências, avaliação e mapa de cobertura do projeto.**
4. **`docs/ROTEIRO-DE-APROPRIACAO.md` — exercícios e perguntas para testar, personalizar e explicar o código com as próprias palavras.**

O módulo Java puro demonstra as ideias didáticas das aulas; a API é uma aplicação original que leva as mesmas ideias a um cenário de checkout. Não é o curso em si: vídeos e slides continuam na plataforma DIO.

**Transparência:** este projeto e seus textos foram preparados com assistência de IA. O [roteiro de apropriação](docs/ROTEIRO-DE-APROPRIACAO.md) propõe testes e alterações para que o estudante confira o funcionamento, acrescente decisões próprias e descreva somente o que realmente fez.

## Requisitos e execução

- JDK 21
- Maven Wrapper incluído

Na raiz do projeto:

```bash
./mvnw test
```

Executar as demonstrações Java puro:

```bash
./mvnw -pl java-puro exec:java -Dexec.mainClass=com.portfolio.dio.CoursePatternsDemo
```

Iniciar a API Spring:

```bash
./mvnw -pl checkout-patterns-api spring-boot:run
```

Consultar [README da API](checkout-patterns-api/README.md) para endpoints, payload de exemplo, catálogo, H2 e decisões de implementação.

## API de checkout

A API disponibiliza `POST /api/orders`, `GET /api/orders` e `GET /api/orders/{id}`. Os pagamentos são inteiramente simulados; não há integração com gateway nem dados financeiros. A persistência usa H2 em memória.

## Testes

Os testes cobrem a instanciação Singleton, a troca de Strategy e a coordenação da Facade em Java puro, além do checkout Spring, persistência, escolha de estratégias e respostas de erro.

## Referências do laboratório

- [Curso e desafio na DIO](https://web.dio.me/lab/explorando-padroes-de-projetos-na-pratica-com-java/learning/12f4a1a6-714d-442e-8fe3-141a7f7a63da?back=/play)
- [Referência oficial DIO — Java puro](https://github.com/digitalinnovationone/lab-padroes-projeto-java)
- [Referência oficial DIO — Spring](https://github.com/digitalinnovationone/lab-padroes-projeto-spring)
