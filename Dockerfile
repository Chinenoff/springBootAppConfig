FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8081
COPY target/springBootAppConfig-0.0.1-SNAPSHOT.jar prodapp.jar
CMD  ["java","-jar","prodapp.jar"]