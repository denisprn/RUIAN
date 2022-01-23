FROM openjdk:17
COPY build/libs/RUIAN-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "RUIAN-0.0.1-SNAPSHOT.jar"]
