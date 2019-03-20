# Geoquizz_backend
Backend exposant les API REST de l’atelier.

## Installation
### Avec docker
Pour lancer l'ensemble de l'architecture, une seule commande à la racine du projet suffit :
```
docker-compose up
```

Cette commande lance 3 containers docker qui exposent chacun :  
  
* la base de données MySql initialisée et prête à l'emploie  
* l'API de gestion des photos sur le port 8080
* l'API de gestion du web player sur le port 8084

### Sans docker
Si il vous est impossible d'utiliser docker, veuillez suivre cette procédure pour tester en local :  
  
* Créez une nouvelle base de données MySql sur votre hote local nommée `geoquizz` et executez le script de création des tables `data.sql` que vous trouverez à la racine du projet  
* Ouvrez les 2 API dans votre IDE pour aller y modifier les `application.properties` et y preciser les valeurs du port avec `server.port` ainsi que des drivers JDBC avec `spring.datasource.url`. Pensez également à entrer vos credentials MySql
  
* Assurez vous que MySql soit en cours d'execution
* Lancez les API en vous rendant à la racine de chacune d'entre elles et executez 
```
mvn -DskipTests clean install
mvn spring-boot:run
```

## Documentation
La documentation de nos APIs est generée avec Swagger.  

### L'API de gestion des photos  
Rendez vous l'adresse suivante pour la consulter :  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### L'API de gestion du web player  
Rendez vous l'adresse suivante pour la consulter :  
[http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)
