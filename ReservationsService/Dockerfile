FROM gradle:8.11-jdk17 AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY ReservationsService/build.gradle ./ReservationsService/
COPY ReservationsService/src ./ReservationsService/src

RUN gradle ReservationsService:build -x test

FROM amazoncorretto:17.0.12-al2023

WORKDIR /app

COPY --from=build /app/ReservationsService/build/libs/*.jar hotel_rs.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "hotel_rs.jar"]