FROM adoptopenjdk/openjdk11:alpine
COPY build/libs/project-0.0.2.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]