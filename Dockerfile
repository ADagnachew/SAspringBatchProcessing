FROM maven:latest
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
COPY ./wait_for_it.sh /app/wait_for_it.sh
RUN chmod +x ./wait_for_it.sh
CMD ["java","-jar","app.jar"]