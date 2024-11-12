FROM amazoncorretto:17

WORKDIR /app

ARG JAR_FILE=RoomService/build/libs/RoomService-1.0.3.jar

COPY ${JAR_FILE} /app/hotel_r.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/hotel_r.jar"]