# Employee Service
This service provides add, update, delete employees and find an employee by its uuid.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

# Running the application locally

From terminal, the docker image must be run as

```shell
cd docker/local
```
 ```shell
 docker-compose up -d
 ```
 
After that, from maven the project must be started as

```shell
mvn spring-boot:run -Dspring.profiles.active=local
```

For running the tests

```shell
mvn spring-boot:run -Dspring.profiles.active=test
```

# Opening Swagger

After running the project, swagger link is: [localhost:8019/swagger-ui.html](localhost:8019/swagger-ui.html)

# Basic Authentication

For calling the services, username and password must be entered as

* user: admin
* password: RoadHouse2019
