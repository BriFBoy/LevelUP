FROM openjdk:21

WORKDIR /app

COPY ./.builds/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]