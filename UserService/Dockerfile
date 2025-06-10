FROM gradle:8.11-jdk17 AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY UserService/build.gradle ./UserService/
COPY UserService/src ./UserService/src

RUN gradle UserService:build

FROM amazoncorretto:17.0.12-al2023

WORKDIR /app

COPY --from=build /app/UserService/build/libs/*.jar hotel_u.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "hotel_u.jar"]
