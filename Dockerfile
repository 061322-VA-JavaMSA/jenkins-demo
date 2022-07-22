FROM openjdk:8-jdk-alpine
COPY target/task-manager.jar  task-manager.jar
ENTRYPOINT ["java", "-jar", "/task-manager.jar"]