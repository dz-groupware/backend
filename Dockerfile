FROM openjdk:11
COPY ./build/libs/project.jar /app/project.jar
CMD ["java", "-jar", "/app/project.jar"]