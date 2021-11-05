FROM openjdk:8
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} GpsApi.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "GpsApi.jar"]