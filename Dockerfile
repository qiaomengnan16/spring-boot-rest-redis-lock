FROM java:8
RUN mkdir /app
WORKDIR /app
COPY target/spring-boot-rest-redis-lock-1.0-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java" , "-jar" , "spring-boot-rest-redis-lock-1.0-SNAPSHOT.jar"]