FROM openjdk:11-jre

COPY build/libs/*.jar app.jar

ENV USE_PROFILE dep

ENV JAVA_TOOL_OPTIONS "-Dspring.profiles.active=${USE_PROFILE}"

ENTRYPOINT ["java","-jar","app.jar"]
