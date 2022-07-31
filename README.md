# faker-events-connector
Faker Events Connector é um Source Connector para o Kafka Connect que gera eventos fake para um  tópico Kafka.

## Comandos
```bash
# cria os pacotes da aplicação
mvn clean compile

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
