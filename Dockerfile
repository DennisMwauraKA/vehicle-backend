#Build Stage
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /build

#copy pom xml and download dependencies

COPY pom.xml ./
RUN mvn dependency:go-offline


#copy the source code andpackage the application

COPY src ./src
RUN mvn clean package -DskipTests

#runtime stage

FROM amazoncorretto:23

#expose arguments
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

#setup working directory
WORKDIR /app

#copy the package jar file
COPY --from=build /build/target/vehiclerental-${APP_VERSION}.jar app.jar

#expose port
EXPOSE 7000

#environment variables
ENV DB_URL=jdbc:postgresql://postgres-vehicle-db:5432/vehicleRentalManagementDB
ENV ACTIVE_PROFILE=$PROFILE


## Run the application
CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "app.jar"]