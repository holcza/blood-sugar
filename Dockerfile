FROM adoptopenjdk:16-jdk-hotspot
WORKDIR /opt/app
COPY target/*.jar blood-sugar.jar
CMD ["java", "-jar", "blood-sugar.jar"]