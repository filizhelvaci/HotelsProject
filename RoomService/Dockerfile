FROM gradle:8.11-jdk17 AS build

WORKDIR /app

COPY dependencies.gradle .
COPY build.gradle .
COPY settings.gradle .
COPY RoomService/build.gradle ./RoomService/
COPY RoomService/src ./RoomService/

RUN gradle RoomService:build

FROM amazoncorretto:17.0.12-al2023

WORKDIR /app

COPY --from=build /app/RoomService/build/libs/*-plain.jar /app/room-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/room-service.jar"]
