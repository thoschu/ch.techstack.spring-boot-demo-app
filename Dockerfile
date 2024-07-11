#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package


#
# Serve stage
#

FROM maven:3.8.3-openjdk-17

COPY --from=build /home/app/target/spring-docker.jar /spring-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/spring-docker.jar"]
