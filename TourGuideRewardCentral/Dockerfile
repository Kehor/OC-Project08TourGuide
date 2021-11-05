FROM openjdk:8
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} RewardApi.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "RewardApi.jar"]