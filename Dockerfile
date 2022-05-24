
FROM openjdk:11
LABEL maintainer="9997ijh@gmail.com"
VOLUME /main-app
COPY build/libs/hug_api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
