FROM amazoncorretto:11-alpine-jdk
MAINTAINER AbhishekSingh612

# Copy spring boot code to container
COPY . /app
# Build the Spring Boot app
RUN ./mvnw clean package
ENTRYPOINT ["java","-jar","target/tweetapp-0.0.1-SNAPSHOT.jar"]
