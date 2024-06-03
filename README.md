### Setup Mongo
* Create folder for mongo data : `mongo-data`
* Run 
```mongod --dbpath mongo-data --port 12312```
* [Mongo Reference](https://spring.io/guides/gs/accessing-data-mongodb)

### Build application
```./gradlew build```

### Run application
```./gradlew bootRun```