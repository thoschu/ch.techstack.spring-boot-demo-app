# ch.techstack.spring-boot-demo-app

## Notices

### Spring
> https://spring.io
> 

#### Spring initializr
> https://start.spring.io
>

#### Spring dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
* spring-web, spring-webmvc, hibernate-validator, json, tomcat, ...

> https://docs.spring.io/spring-boot/index.html

> https://docs.spring.io/spring-boot/how-to/actuator.html
> > http://localhost:8080/actuator
> 
> > http://localhost:8080/actuator/health
>
> > http://localhost:8080/actuator/info
> 
> https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#page-title
>

### Maven

#### Maven Central Repository (remote)
> https://mvnrepository.com/
> 
> https://mvnrepository.com/repos/central
> 
> > ./mvnw help:effective-pom
> 

#### Start the App with Maven
> ./mvnw spring-boot:run
> 

#### Build JAR with Maven and run via Java
> https://maven.apache.org/
> > ./mvnw clean compile test
> 
> > ./mvnw clean package
> 
> > java -jar ./target/spring-boot-demo-app-0.0.1-SNAPSHOT.jar
> 

### Thymeleaf
> https://www.thymeleaf.org/index.html
> 

### Running from the command-line
1. > java -jar ./target/spring-boot-demo-app-0.0.1-SNAPSHOT.jar
2. >./mvnw spring-boot:run
