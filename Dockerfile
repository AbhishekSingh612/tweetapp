FROM amazoncorretto:11-alpine-jdk
MAINTAINER AbhishekSingh612
WORKDIR /app
COPY . /app
RUN chmod u+x ./mvn && ./mvnw clean package
ENTRYPOINT ["java","-jar","app.jar"]
