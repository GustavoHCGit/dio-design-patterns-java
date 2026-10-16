# Roteiro para se apropriar do projeto

Este roteiro é um apoio para estudar e personalizar a entrega. Ele não substitui as aulas e não deve ser apresentado como uma experiência pessoal já realizada. Faça os passos, observe o comportamento e responda às perguntas com as suas próprias palavras antes de enviar o projeto.

## Percurso prático

1. Rode `./mvnw test` na raiz e identifique quais testes pertencem ao módulo `java-puro` e quais pertencem à API Spring.
2. Rode a demonstração com `./mvnw -pl java-puro exec:java -Dexec.mainClass=com.portfolio.dio.CoursePatternsDemo`. Compare a instância compartilhada em cada implementação Singleton.
3. No `Robot`, mude a estratégia mais de uma vez. Acrescente uma nova classe `MovementBehavior` (por exemplo, `StealthBehavior`) e confirme que não precisou alterar `Robot`.
4. No exemplo Facade, acrescente um CEP ao catálogo local. Teste um CEP inválido e verifique que o CRM não registra o cliente quando a busca falha.
5. Acrescente uma estratégia de pagamento à API, por exemplo `WalletPaymentStrategy`. Registre-a no enum e implemente a interface. Teste pelo endpoint se o registro de estratégias a seleciona sem introduzir condicionais na fachada.
6. Acrescente uma regra nova à cadeia de validação do checkout. Crie um teste que falha antes da regra e passa depois de corrigir a entrada.
7. Altere um valor de frete e ajuste ou crie um teste que documente o comportamento esperado. Explique em que classe essa política deve ficar e por quê.
8. Execute a API, crie um pedido com o `curl` do README e consulte-o. Confirme que valores totais e itens persistidos correspondem ao pedido.
9. Leia o código da referência oficial DIO e compare-o com esta implementação. Anote pelo menos uma semelhança e uma escolha sua que foi diferente.
10. Escreva no seu README qual módulo foi mais fácil de compreender, o que teve de alterar para aprender, e que limitação ainda falta resolver. Só inclua observações que você próprio verificou.

## Perguntas para responder com as próprias palavras

- Que responsabilidade o padrão Singleton pretende limitar? Qual é a diferença entre o singleton estático dos exemplos e o ciclo de vida singleton de um bean Spring?
- Como o programa escolheria a política de pagamento sem `PaymentStrategy`? O que muda quando adiciona outra implementação?
- O que a `CheckoutFacade` esconde do controller? Como se percebe que ela não deve conter todos os detalhes de persistência e de cálculo?
- Qual é a fronteira entre `OrderRepository` e `CheckoutFacade`? Como a implementação poderia trocar H2 por PostgreSQL?
- Qual regra de negócio é adequada para a cadeia de validação? O que aconteceria se a mesma regra fosse repetida no controller?
- Quais são as limitações desta demonstração antes de ser usada num sistema real?

## Transparência e integridade

Este código foi preparado com assistência de IA e validado por testes automatizados. A análise e os exercícios ajudam a conferir as decisões, mas não substituem a compreensão do estudante. Não afirme que escreveu sozinho, assistiu a partes que não assistiu, publicou ou testou algo sem o ter feito. Personalize o projeto com alterações próprias e explique-as de forma verdadeira antes de o apresentar como portfólio.
