FROM gradle:8.11-jdk17 AS build

WORKDIR /app

COPY dependencies.gradle .
COPY build.gradle .
COPY settings.gradle .
COPY RoomService/build.gradle ./RoomService/
COPY RoomService/src ./RoomService/src

RUN gradle RoomService:build

FROM amazoncorretto:17.0.12-al2023

WORKDIR /app

COPY --from=build /app/RoomService/build/libs/*.jar hotel_r.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "hotel_r.jar"]
