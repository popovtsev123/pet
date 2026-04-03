FROM gradle:8.14.2-jdk21 AS build
ARG NEXUS_URL=http://host.docker.internal:8081/repository/maven-snapshots
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=admin

ENV NEXUS_URL=${NEXUS_URL}
ENV NEXUS_USERNAME=${NEXUS_USERNAME}
ENV NEXUS_PASSWORD=${NEXUS_PASSWORD}

WORKDIR /home/gradle/project

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src
COPY openapi ./openapi

RUN gradle --no-daemon clean bootJar -x test

RUN test -f build/libs/app.jar || (echo "app.jar not found!" && exit 1)

RUN gradle publish --info

FROM eclipse-temurin:24-jre
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/app.jar app.jar
EXPOSE 8092
CMD ["java", "-jar", "app.jar"]