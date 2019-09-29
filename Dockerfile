FROM openjdk:8-alpine
MAINTAINER Dmitry Mikhailovich dmitry.mikhailovich@gmail.com
ADD build/libs/accounter.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
