FROM gradle:8.14.2-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8094
CMD ["java", "-jar", "app.jar"]