# Docker
Please find below the command to create a container for this project : \
```docker run --name=miagiques_projet -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=miagiquesbd -p 3306:3306 mysql```


Si l'application commence à faire n'importe quoi en sortant des messages d'erreurs en lien avec la base de données il faut : 
- Clean le projet avec le plugin clean et l'instruction clean:clean
- Supprimer l'image docker et la remttre en place

Si cela ne résoud pas le problème, cela signifie qu'un processus tourne en fond, donc il faut soit arriver à le kill ou alors redémarrarer le PC
