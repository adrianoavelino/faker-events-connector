# faker-events-connector
[![build-test](https://github.com/adrianoavelino/kafka-connect-lambda-localstack/actions/workflows/build-test.yml/badge.svg)](https://github.com/adrianoavelino/faker-events-connector/actions/workflows/build-test.yml)
![GitHub top language](https://img.shields.io/github/languages/top/adrianoavelino/faker-events-connector)
[![Repository size](https://img.shields.io/github/repo-size/adrianoavelino/faker-events-connector)](https://img.shields.io/github/repo-size/adrianoavelino/faker-events-connector)
[![Last commit](https://img.shields.io/github/last-commit/adrianoavelino/faker-events-connector)](https://github.com/adrianoavelino/faker-events-connector/commits/master)

Faker Events Connector é um Source Connector para o Kafka Connect que gera eventos fake para um  tópico Kafka.

## Tecnologias
- Java
- Maven
- Docker e docker-compose
- Kafka

## Começando
Crie o jar do plugin:
```bash
mvn clean package
```

Inicie os containers:
```bash
docker compose up -d
```

> [!NOTE]
> Aguarde a incialização do serviço **connect**, se necessário execute `docker compose logs -f` para analisar os logs

Liste os plugins instalados:
```bash
curl -X GET http://localhost:8083/connector-plugins
```
> [!WARNING]
> Verifique a existência da classe `br.com.fec.FecConnector`

Crie um conector:
```bash
curl -X POST -H "Content-Type:application/json" -d @examples/basic-example.json http://localhost:8083/connectors
```

Liste os conectores criados:
```bash
curl -X GET http://localhost:8083/connectors
```

Crie um consumer para visualizar o eventos gerados pelo conector:
```bash
docker compose exec kafka kafka-console-consumer \
--bootstrap-server kafka:9092 --topic topico --from-beginning \
--property print.key=true --property print.value=true
```

Liste os tópicos kafka existentes, caso necessário:
```bash
docker compose exec kafka kafka-topics describe --list \
--bootstrap-server kafka:29092
```
