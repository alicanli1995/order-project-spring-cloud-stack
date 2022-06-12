## This project is being implemented as a project that includes all the spring microservices, cloud, messaging systems that I have learned about.

## Proje yapım aşamasındadır aralıklarla güncellenecek bitince tam dökümantasyon eklenecek genel yapı ve içereceği teknolojiler aşağıdaki gibidir.

<p align="center">
<img src="img/diagram.jpg" alt="ci" width="1000" class="center"/>
</p>


### Invoice Service Generate PDF ->  Samples Output -> 

<p align="center">
<img src="img/invoice.jpg" alt="ci" width="1000" class="center"/>
</p>


# What technologies exist ? 
    
    Integration Tests with TestContainers And JUnit 5
    Gateway Service
    Auth Server with Keycloak
    NoSQL Database ( Apache Cassandra )
    SQL Database ( PostgreSQL)
    Messaging Systems ( RabbitMQ, Kafka , Spring Cloud Stream)
    Resilience pattern with Resilience4J 
    Eureka registry service
    Config server with git repository
    Vault server for store secret informations
    Zipkin for distributed tracing
    ELK (Elasticsearch , Logstash , Kibana) Stack for distributed logging
    Statistic service with Async communication -> Kafka or RabbitMQ
    Sync and Async communication 
    Exception Handling
    Layered Architecture
    Dockerize all project ( maybe not implementing )
