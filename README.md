# demo-microservices-springboot
My take on backend microservices architecture using Spring Boot Cloud.
The plan is to cover as many topics, approaches and tools, as possible.
Inspired by "Programming Techie" Youtube channel.

## domain services:
- customer-service:
  - registering customers
  - manual management
- credit application service
  - validating
  - registering
  - manual management
- credit application processing service
  - batch processing
  - automatic management of users and applications
  - reporting
- p2p payments service?

## key entities:
- customer
  - customer addresses
  - customer accounts
  - customer transactions
- credit application
- credit application processing job

## TO-DO list:

### project features
- api-gateway
- authentication
- ~~service-discovery (Eureka)~~
- distributed tracing
- feign clients?
- a bit of event-driven architecture (Kafka or RabbitMQ)
- data flows, batch processing (Spring Batch, maybe Apache Camel)
- domain services
- storage:
  - ~~SQL~~
  - ~~noSQL~~
  - transactions
  - distributed transactions
    - choreography
    - orchestration
- build and deployment
  - Docker
  - K8S
  - CI/CD
- logging
  - ELK stack
- testing
  - unit
  - Spring Boot Test
  - Testcontainers
