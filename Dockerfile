FROM openjdk:17-alpine
RUN apk --no-cache add curl
COPY target/*.jar rest-http.jar
CMD java ${JAVA_OPTS} -jar rest-http.jar
