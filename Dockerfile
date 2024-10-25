FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY target/ordering-0.0.1-SNAPSHOT.jar ordering-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","ordering-0.0.1-SNAPSHOT.jar"]