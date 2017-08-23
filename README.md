smart-simulator
==

MongoDB Installation
-------------
enable MongoDB remote access first.

open mongo.exe client, and execute this to create a db user with readWrite privilege.
```
use test

db.createUser( { user: "spie", pwd: "18Mdream", roles: [ { role: "readWrite", db: "test" } ]})
```


Edit application.properties
----
```	
databaseName=<your value>
serverAddress=<your ip or host name>
port=27017
user=spie
password=18Mdream
```

Index Page
--
<http://localhost:8083/>


Alternative for Docker
----
```
mkdir -p /data/db
mkdir -p /data/configdb
docker pull mongo:3.4
docker container run --name mongo -d -p 27017:27017 -v /data/db:/data/db  -v  /data/configdb:/data/configdb mongo 
docker exec -it mongo bash
mongo
use test
db.createUser( { user: "spie", pwd: "18Mdream", roles: [ { role: "readWrite", db: "test" } ]})
```
Reference
----
https://github.com/docker-library/mongo/blob/9914fd4e7967c32ad79710b08e4a21f4f68239f9/3.4/Dockerfile

