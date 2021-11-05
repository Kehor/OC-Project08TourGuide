FROM openjdk:8
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} TourGuide.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TourGuide.jar"]