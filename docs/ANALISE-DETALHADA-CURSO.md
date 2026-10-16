# Análise detalhada do laboratório DIO — Design Patterns com Java

## O que foi possível verificar

Na página autenticada do laboratório, a trilha lista dez conteúdos: apresentação inicial; padrões de projeto; Singleton, Strategy e Facade em Java puro; introdução ao Spring; conhecimento do projeto-base; padrões numa API REST; desafio; e explicação do desafio [1]. A mesma página descreve três caminhos para o trabalho final: reproduzir e evoluir o projeto de aula, criar uma ideia nova com Java/Spring ou concentrar-se num padrão.

O conteúdo textual das aulas em vídeo não veio acompanhado de transcrições legíveis. O link de slides listado no desafio também não abriu durante a consulta. Para analisar as práticas, comparei os títulos e a descrição do curso com os dois repositórios oficiais vinculados pela DIO e com a estrutura e os fontes públicos desses repositórios [2] [3]. Portanto, as seções abaixo separam **evidência no material público** de **inferência pelo título**. Não atribuem frases ou explicações específicas aos instrutores.

## Percurso aula a aula

### 1. Apresentação inicial

**O que se pode afirmar:** o título posiciona a jornada como uma exploração prática de padrões de projeto com Java, desde os padrões clássicos até aplicações com Spring. A descrição do desafio pede que a pessoa use os conceitos para criar algo que agregue valor ao portfólio [1].

**Limite da análise:** sem transcrição ou texto da gravação, não é possível confirmar a agenda, os pré-requisitos ou as explicações exatas da apresentação inicial. No projeto, esta etapa é refletida pela documentação da raiz, que explica a divisão entre exemplos Java puro e a API Spring.

### 2. Padrões de Projeto

Os materiais oficiais não formam um catálogo de todos os GoF. O README do exemplo Java puro declara três padrões: **Singleton, Strategy e Facade**. O README Spring declara **Singleton, Strategy/Repository e Facade** [2] [3].

A forma útil de estudar esses nomes é perguntar qual responsabilidade cada padrão resolve e qual custo de desenho introduz. Um padrão não é uma classe que se inclui por obrigação: é uma forma de organizar responsabilidades quando o problema pede aquela forma. Essa distinção importa especialmente no exemplo Singleton, que pode ser demonstrado com uma instância estática em Java puro, mas costuma ser gerido pelo container no Spring [4].

### 3. Praticando com Java Puro: Singleton

O repositório oficial contém três formas: `SingletonEager`, `SingletonLazy` e `SingletonLazyHolder`. A primeira cria a instância como campo estático; a segunda tenta criá-la no primeiro acesso; a terceira usa uma classe interna holder para adiar a criação [2].

Um detalhe importante aparece ao ler o código, não apenas o nome da aula: `SingletonLazy` do repositório oficial não sincroniza o acesso ao campo. Duas threads podem observar a ausência da instância ao mesmo tempo e construir objetos diferentes. A variante holder evita essa condição de corrida aproveitando a inicialização sincronizada de classes Java [5]. O teste de demonstração oficial chama cada método mais de uma vez e imprime o resultado, mas não é um teste de concorrência.

**No projeto:** `java-puro` mantém as três variações para comparação e adiciona testes de identidade. A variante lazy usa `volatile` e double-checked locking, para que a demonstração não reproduza o risco concorrente da versão de referência sem o explicar.

### 4. Praticando com Java Puro: Strategy

No repositório oficial, `Robo` mantém um objeto do tipo `Comportamento` e delega a ele a ação de mover. Implementações concretas — normal, defensiva e agressiva — podem ser substituídas durante a execução [2]. O ponto central é que o contexto (`Robo`) não precisa concentrar todos os comportamentos num bloco crescente de condicionais.

**No projeto:** `Robot` aceita `MovementBehavior` e troca a implementação entre normal, defensiva e agressiva. O teste verifica a troca em tempo de execução. Na API Spring, o mesmo princípio aparece em estratégias de pagamento e de frete, aplicadas a outro domínio. São exemplos de implementação do projeto, não alegações sobre o conteúdo da gravação.

### 5. Praticando com Java Puro: Facade

A `Facade` da referência oferece `migrarCliente(nome, cep)`. Por trás dessa chamada, consulta o subsistema CEP e passa os dados obtidos ao CRM [2]. Assim, quem usa a fachada não precisa orquestrar diretamente essa sequência.

A leitura do código também revela limites instrutivos: a implementação de CEP usa dados fixos e o serviço CRM imprime informações; não há chamada a um provedor real nem persistência num CRM real [2]. Isso é adequado para demonstrar o desenho, mas não deve ser descrito como uma integração de produção.

**No projeto:** `CustomerMigrationFacade` coordena um catálogo local de CEP e o serviço CRM. Há um teste do fluxo válido e outro que verifica que um CEP desconhecido não registra o cliente.

### 6. Praticando com Spring: Introdução

O repositório oficial permite observar o contexto da aplicação: classe `Application` com `@SpringBootApplication`, configuração e dependências do ecossistema Spring [3]. O que não é possível verificar sem o vídeo ou sua transcrição é a sequência exata da explicação introdutória.

A principal ponte conceitual para o restante do laboratório é a injeção de dependências: componentes são montados pelo container em vez de serem instanciados manualmente pelo cliente. A documentação do Spring explica que `singleton` é o escopo padrão dos beans e significa uma instância por definição de bean e por container de aplicação — não uma instância global por classe, como no Singleton GoF [4].

**No projeto:** a API usa beans singleton gerenciados pelo Spring e injeção por construtor. Ela não implementa `getInstance()` nos serviços.

### 7. Praticando com Spring: Conhecendo o Projeto Base

A estrutura oficial distribui responsabilidades entre aplicação, controller, modelos, repositories e serviços. O `pom.xml` de referência usa Spring Boot 2.5.4 e Java 11, além de Spring Web, Spring Data JPA, OpenFeign e H2 [3]. Essa configuração explica a base didática de uma API REST com persistência e consulta de CEP.

É uma referência histórica útil, mas não convém copiá-la sem avaliar versões e dependências para um projeto novo. A API desta entrega usa Spring Boot 3.4.5, Java 21, validação HTTP, DTOs e testes de integração. Essa atualização de stack não altera o objetivo de aprender as responsabilidades demonstradas.

### 8. Praticando com Spring: Padrões de Projeto em uma API REST

A referência Spring inclui um controller de clientes, repositories para cliente e endereço, um contrato de serviço e sua implementação. `ClienteServiceImpl` coordena a leitura e gravação nos repositories e o serviço ViaCEP; o repositório de cliente estende `CrudRepository` [3]. A API mostra, portanto, como as abstrações do Spring podem desempenhar papéis relacionados a Strategy, Repository, Facade e ao escopo singleton do container.

A distinção de vocabulário é importante: o README oficial agrupa “Strategy/Repository”, mas `Repository` é também uma abstração da camada de dados promovida por Spring Data, enquanto Strategy descreve a substituição de uma política/algoritmo por outra. A documentação oficial do Spring Data explica a função da abstração Repository e das interfaces CRUD [6].

**No projeto:** a API de checkout troca o domínio de clientes/endereços pelo de pedidos. `CheckoutFacade` coordena o caso de uso; `OrderRepository` trata persistência; registries selecionam estratégias de pagamento e envio. A cadeia de validação é uma extensão adicional.

### 9. Desafio de Projeto

A página do laboratório dá liberdade de escopo: reproduzir e evoluir, criar do zero, ou concentrar-se num padrão, citando Chain of Responsibility como exemplo [1]. As referências oficiais são sugeridas como base de estudo, não como substituto da explicação do próprio trabalho.

**No projeto:** a escolha foi combinar reprodução didática com uma aplicação original: os exemplos Java puro refletem os contextos do laboratório; a API de checkout acrescenta um caso de uso Spring e Chain of Responsibility. O conjunto inclui testes e instruções para que a implementação possa ser inspecionada.

### 10. Entendendo o Desafio

A parte mais importante para uma boa entrega é justificar cada decisão com o problema resolvido. A documentação do repositório deve permitir que outra pessoa entenda onde está o padrão, o que o contexto delega, como adicionar uma variante e quais limitações a demonstração mantém. O desafio pede valor de portfólio e recomenda referências e GitHub [1].

Uma ficha pública da DIO associada ao projeto também descreve a expectativa de aplicar vários padrões e documentar quais foram escolhidos e por quê [7]. Como a página da trilha permite escopos diferentes, deve-se seguir o enunciado visível na própria submissão e ser transparente sobre o que a implementação realmente contém.

**No projeto:** os READMEs mapeiam padrões a classes, explicam como executar e deixam explícito que pagamentos são simulados e que H2 é apenas para demonstração.

## Síntese crítica

A sequência tem um mérito claro: o estudante primeiro vê a forma explícita dos padrões em Java puro e depois observa como Spring oferece container, injeção e abstrações de persistência. Isso favorece transferência de conhecimento. Os exemplos são pequenos o suficiente para seguir o fluxo de chamadas e localizar os papéis de contexto, estratégia, fachada e repositório [2] [3].

O alcance, porém, é seletivo. A prática cobre poucos padrões e não constitui uma formação completa em GoF. Os exemplos de Facade e integração são simplificados; o de Singleton lazy não é seguro sob concorrência; e a referência Spring usa uma geração antiga do ecossistema. Esses limites não invalidam o laboratório: mostram precisamente a diferença entre exercício didático e software pronto para produção.

Uma aprendizagem mais sólida aparece ao conseguir explicar trade-offs, não apenas recitar nomes. Singleton estático facilita acesso, mas pode esconder dependências e estado compartilhado. Strategy reduz condicionais e permite extensão, mas várias classes pequenas só se justificam quando há políticas realmente intercambiáveis. Facade simplifica o uso, mas não deve absorver todas as regras de negócio. Repository isola persistência, mas não elimina a necessidade de decidir transações e carregamento dos dados.

## Como tornar a entrega genuinamente sua

O projeto e esta análise foram preparados com assistência de IA. Isso não demonstra, por si só, que a pessoa compreendeu o conteúdo ou que fez as alterações descritas. Para transformar a entrega num trabalho que você possa explicar com segurança:

1. Execute `./mvnw clean verify` e a demonstração Java puro. Leia os testes e confira o que cada um realmente comprova.
2. Implemente uma estratégia nova de robô **por conta própria** e escreva um teste para ela.
3. Adicione uma estratégia nova de pagamento à API e explique como o registro a encontra sem alterar o fluxo da fachada.
4. Crie uma validação adicional na Chain of Responsibility e teste entradas válidas e inválidas.
5. Compare o seu código com os repositórios oficiais. Registre no README uma diferença de desenho que você escolheu e a razão, sem afirmar que a referência faz algo que não faz.
6. Responda às perguntas do [roteiro de apropriação](ROTEIRO-DE-APROPRIACAO.md) com as suas próprias observações. Substitua exemplos genéricos por alterações que você realmente implementou.
7. Revise a descrição antes de enviar à DIO. Não afirme que assistiu a uma aula, escreveu sozinho, executou um teste ou integrou um serviço se isso não for verdade.

## Referências

[1]: https://web.dio.me/lab/explorando-padroes-de-projetos-na-pratica-com-java/learning/12f4a1a6-714d-442e-8fe3-141a7f7a63da?back=/play "Trilha e descrição do desafio DIO"
[2]: https://github.com/digitalinnovationone/lab-padroes-projeto-java "Repositório oficial DIO — Java puro"
[3]: https://github.com/digitalinnovationone/lab-padroes-projeto-spring "Repositório oficial DIO — Spring"
[4]: https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html "Documentação Spring — escopos de beans"
[5]: https://docs.oracle.com/javase/specs/jls/se21/html/jls-12.html#jls-12.4.2 "Java Language Specification — inicialização de classes"
[6]: https://docs.spring.io/spring-data/commons/reference/repositories/core-concepts.html "Documentação Spring Data — conceitos de Repository"
[7]: https://www.dio.me/projects/explorando-padroes-de-projetos-na-pratica-com-java "Ficha pública DIO — projeto de padrões com Java"
