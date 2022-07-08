FROM maven:3.8-openjdk-8 as builder
COPY src/ src/
COPY pom.xml pom.xml
RUN mvn package

FROM tomcat:8.5 as runner
COPY --from=builder target/task-manager.war target/task-manager.war 
COPY target/task-manager.war /usr/local/tomcat/webapps/