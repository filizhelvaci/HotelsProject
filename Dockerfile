FROM amazoncorretto:17

WORKDIR /app

ARG JAR_FILE=RoomService/build/libs/RoomService-v.0.2.jar

COPY ${JAR_FILE} /app/hotel_r.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/hotel_r.jar"]

