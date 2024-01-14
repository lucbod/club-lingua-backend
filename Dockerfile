FROM openjdk:17-jdk

WORKDIR /app

COPY target/clublingua-0.0.1-SNAPSHOT.jar clublingua.jar

EXPOSE 8080

CMD ["java", "-jar", "clublingua.jar"]
