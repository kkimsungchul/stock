FROM openjdk:8-jdk-alpine
ARG JAR_FILE=stock-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} myboot.jar
ENTRYPOINT ["java","-jar","/myboot.jar"]