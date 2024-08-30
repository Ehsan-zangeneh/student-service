# Dockerfile

#java version 17
FROM docker.arvancloud.ir/openjdk

#Working directory
WORKDIR /student-service

#copy my app jar file to the container
COPY build/libs/student-service-1.0-SNAPSHOT.jar student-service.jar

# application will run on this port
EXPOSE 8380

# Run the application
ENTRYPOINT ["java", "-jar", "student-service.jar"]