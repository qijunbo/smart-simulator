
# smart-simulator
Installation
-------------
enable MongoDB remote access first.

open mongo.exe client, and execute this to create a db user with readWrite privilege.

use test

db.createUser( { user: "spie", pwd: "18Mdream", roles: [ { role: "readWrite", db: "test" } ]})


#Index Page
<http://localhost:8083/>