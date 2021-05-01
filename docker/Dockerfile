FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=ubix-node-monitor.jar
RUN apk update && apk upgrade && rm -rf /var/cache/apk/*

WORKDIR /opt/app

COPY ca-sectigo.der ca2.der
RUN keytool -importcert -noprompt -alias ca-ubikiri-2 -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -file ca2.der

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
