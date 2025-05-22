FROM openjdk:21 AS build
WORKDIR /build
COPY . .
RUN ./mvnw package -Dmaven.test.skip

FROM openjdk:21
WORKDIR /app
COPY --from=build /build/.builds/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]