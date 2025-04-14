FROM openjdk:21
LABEL authors="defix"

WORKDIR /app
COPY ./target/current-project-ziptegrity-remake-0.0.2-SNAPSHOT.jar app.jar
EXPOSE 3030

ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]
