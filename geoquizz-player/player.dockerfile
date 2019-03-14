FROM java:8
LABEL maintainer=anthony.zink@outlook.fr”
EXPOSE 8080
ADD target/geoquizz-player-0.0.1-SNAPSHOT.jar geoquizz-player-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","geoquizz-player-0.0.1-SNAPSHOT.jar"]