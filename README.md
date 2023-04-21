Solução da avaliação da TM, foi criado um serviço para listagem de transferências e agendamento de transferências

### Tecnologias:
 - Java 17
 - Kotlin 1.7.22
 - jUnit
 - Maven
 - SpringBoot

### Como executar o projeto:

Rodando o comando: 
- mvn spring-boot:run

ou

- Clicar em run na classe main

### Endpoint:

- POST /transfer (Agendamento de uma transação)

- GET  /transfer/{shippingAccount} (Listagem das transferências realizadas da conta origem)

#### Exemplo de chamada POST:

curl --location 'http://localhost:8080/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "shippingAccount": 12345,
    "destinationAccount": 54321,
    "value": 3000.0,
    "taxType": "A",
    "scheduleDate": "2023-04-20"
}'

### Cenários validados:

- Cenário 1: Agendar uma transferência do tipo A para data de hoje com sucesso
- Cenário 2: Agendar uma transferência do tipo A para data futura e visualizar erro de data invalida
- Cenário 3: Agendar uma transferência do tipo B para data entre 1 até 10 dias futuros com sucesso
- Cenário 4: Agendar uma transferência do tipo B para data de 11 ou mais dias futuros e visualizar erro de data invalida
- Cenário 5: Agendar uma transferência do tipo B para data atual e visualizar erro de data invalida
- Cenário 6: Agendar uma transferência do tipo C para data entre 11 até 20 dias futuros com sucesso e validar taxa regressiva
- Cenário 7: Agendar uma transferência do tipo C para data entre 21 até 30 dias futuros com sucesso e validar taxa regressiva
- Cenário 8: Agendar uma transferência do tipo C para data entre 31 até 40 dias futuros com sucesso e validar taxa regressiva
- Cenário 9: Agendar uma transferência do tipo C para data de 41 ou mais dias futuros com sucesso e validar taxa regressiva
- Cenário 10: Agendar uma transferência do tipo D com valor até $1000,00 com sucesso e validar aplicação da taxa do tipo A
- Cenário 11: Agendar uma transferência do tipo D com valor de $1001 até $2000 com sucesso e validar aplicação da taxa do tipo B

Para os cenários abaixo apenas alterar a data de agendamento da transferência:
- Cenário 12: Agendar uma transferência do tipo D com valor de $2001 com sucesso e validar aplicação da taxa do tipo C 8.2%
- Cenário 13: Agendar uma transferência do tipo D com valor de $2001 com sucesso e validar aplicação da taxa do tipo C 6.9%
- Cenário 14: Agendar uma transferência do tipo D com valor de $2001 com sucesso e validar aplicação da taxa do tipo C 4.7%
- Cenário 15: Agendar uma transferência do tipo D com valor de $2001 com sucesso e validar aßßplicação da taxa do tipo C 1.7%
- Cenário 16: Agendar uma transferência com um valor maior que o saldo e visualizar saldo insuficiente