FROM java:8
VOLUME /tmp
CMD [ "mvn clean install" ]
ADD target/geoquizz-player-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]