FROM amazoncorretto:11-alpine-jdk
MAINTAINER AbhishekSingh612
# Build the Spring Boot app
RUN ./mvnw clean package
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
