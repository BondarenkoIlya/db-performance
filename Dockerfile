FROM openjdk:11

COPY build/libs/db-performance-*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]