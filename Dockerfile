# Uygulama için JDK gerekli
FROM amazoncorretto:17

WORKDIR /app
# Bizim bu projemizdeki JAR dosyamızın, Docker içinde çalışacağı konumu
ARG JAR_FILE=RoomService/build/libs/RoomService-v.0.0.1.jar

# JAR dosyasını root klasörüne bu isimle kopyala
COPY ${JAR_FILE} /app/hotel_r.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/hotel_r.jar"]

