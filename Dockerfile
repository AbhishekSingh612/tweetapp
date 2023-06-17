FROM amazoncorretto:11-alpine-jdk
MAINTAINER AbhishekSingh612
COPY app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
