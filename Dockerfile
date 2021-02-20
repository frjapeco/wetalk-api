FROM openjdk:8-jre-alpine3.9
COPY target/wetalk-api-1.0-SNAPSHOT.jar /wetalk-api.jar
CMD ["java", "-jar", "/wetalk-api.jar"]