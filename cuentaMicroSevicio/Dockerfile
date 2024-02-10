FROM openjdk:17-jdk

WORKDIR /app

COPY target/cuentamicroservicio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8282

CMD ["java", "-jar", "app.jar"]
