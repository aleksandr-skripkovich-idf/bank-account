FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/bank-account-1.0.jar bank-account-1.0.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "bank-account-1.0.jar"]