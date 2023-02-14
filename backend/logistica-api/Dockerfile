FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY target/logistica-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
LABEL version="0.0.1" description="Disponibilizando a logistica-api" maintainer="Jorge Amaro<inf.amaro.jc@gmail.com>"  