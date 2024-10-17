# faker-events-connector
[![build-test](https://github.com/adrianoavelino/kafka-connect-lambda-localstack/actions/workflows/build-test.yml/badge.svg)](https://github.com/adrianoavelino/faker-events-connector/actions/workflows/build-test.yml)
![GitHub top language](https://img.shields.io/github/languages/top/adrianoavelino/faker-events-connector)
[![Repository size](https://img.shields.io/github/repo-size/adrianoavelino/faker-events-connector)](https://img.shields.io/github/repo-size/adrianoavelino/faker-events-connector)
[![Last commit](https://img.shields.io/github/last-commit/adrianoavelino/faker-events-connector)](https://github.com/adrianoavelino/faker-events-connector/commits/master)

Faker Events Connector é um Source Connector para o Kafka Connect que gera eventos fake para um  tópico Kafka.

## Comandos
```bash
# cria os pacotes da aplicação
mvn clean package

# inicia os containers
docker-compose up -d

# lista os plugins instalados
curl -X GET http://localhost:8083/connector-plugins

# cria um conector
curl -X POST -H "Content-Type:application/json" -d @examples/basic-example.json http://localhost:8083/connectors

# lista os conectores criados
curl -X GET http://localhost:8083/connectors

# lista os tópicos do kafka
docker-compose exec kafka-topics describe --list \
--bootstrap-server kafka:29092

# cria um consumer
docker-compose exec kafka kafka-console-consumer \
--bootstrap-server kafka:9092 --topic topico --from-beginning \
--property print.key=true --property print.value=true
```
