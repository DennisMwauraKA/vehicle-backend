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
EXPOSE 8080
ENV PORT=8080
#environment variables
ENV DB_URL=jdbc:postgresql://postgres-vehicle-db:5432/vehicleRentalManagementDB
ENV ACTIVE_PROFILE=$PROFILE


## Run the application
# ===== Build Stage =====
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and package the application
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM amazoncorretto:23
WORKDIR /app

# Arguments
ARG APP_VERSION=1.0.0
ARG PROFILE=dev

# Copy the packaged jar file
COPY --from=build /build/target/vehiclerental-${APP_VERSION}.jar app.jar

# Expose port (Render uses dynamic port via $PORT)
EXPOSE 8080
ENV PORT=8080

# Default environment variables
ENV DB_URL=jdbc:postgresql://postgres-vehicle-db:5432/vehicleRentalManagementDB
ENV ACTIVE_PROFILE=$PROFILE

# Run the application
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} -Dserver.port=${PORT} -jar app.jar"]
