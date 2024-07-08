# ch.techstack.spring-boot-demo-app

## Notices

### spring initializr
> https://start.spring.io
> 

### Start with Maven
> ./mvnw spring-boot:run
> 

### Build JAR with Maven
> ./mvnw clean package
> 
> java -jar target/spring-docker.jar
> 
> ./mvnw clean package && java -jar target/spring-docker.jar
> 

### MySQL
> https://hub.docker.com/_/mysql
> 
> docker compose up -d
> 

### MySQL Workbench
> https://www.mysql.com/products/workbench/
> 

### Jackson Project
> https://github.com/FasterXML/jackson
> 

### Hibernate ORM
> https://hibernate.org/orm/
> 

### Docker
> docker build -t thoschu/spring-boot-demo-app .
> 
> docker buildx build -f Dockerfile --platform linux/arm64 --tag thoschu/spring-boot-demo-app:latest --load .
> 
> docker run -d -p 81:8080 --name demo-app thoschu/spring-boot-demo-app
> 
> docker run -p 81:8080 --name demo-app --network my-network -t thoschu/spring-boot-demo-app 
> 