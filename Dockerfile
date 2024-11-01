FROM gradle:7.6.2-jdk17-alpine AS build

COPY build.gradle.kts settings.gradle.kts dependencies.gradle.kts /home/gradle/app/

COPY src /home/gradle/app/src

WORKDIR /home/gradle/app

RUN gradle clean build

COPY --from=build /home/gradle/app/src/main/resources/serviceAccountKey.json /src/main/resources/serviceAccountKey.json

COPY --from=build /home/gradle/app/build/libs/*.jar /app/hotel_r.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/hotel_r.jar"]
