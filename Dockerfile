FROM openjdk:17
COPY build/libs/ruian-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "ruian-0.0.1-SNAPSHOT.jar"]
