FROM maven:3.8-openjdk-11 as builder
COPY src/ src/
COPY pom.xml pom.xml
RUN mvn package
RUN ls target/

FROM tomcat:8.5 as runner
COPY --from=builder target/task-manager.war /usr/local/tomcat/webapps/