# DB-Performance

Check SQL query performance application.

Performance metrics:
1. Execution time

##API

Application:
* GET /queries - get all
* POST /queries - publish query to check
* GET /queries/{uuid} - get by uuid

Third party:
* Swagger - http://localhost:8080/swagger-ui.html#
* RabbitMQ - http://localhost:15672/#

##Components

Databases:
1. PostgreSQL
2. MSSQL

Both databases test table scheme:


##How to:
###build

Build gradle project
```
./gradlew clean build
```

Run integration tests
```
./gradlew integrationTest
```

###start

Start application after build
```
./docker-compose up
```

##Demo

Steps:
1. Go to swagger and create new query through post request on /queries. Query could perform performance test only according to test table scheme.
```
{ "query": "Select * from test_table" }
```
2. Fetch new query's UUID from response header "Location"
3. Request query by uuid.
4. Check out performance test result for every supported databases
```
{
  "id": "33299aa6-5447-4dec-be5b-31fc27ee5b79",
  "query": "Select * from test_table",
  "version": 0,
  "perfResults": [
    {
      "databaseType": "postgresql",
      "executionTime": 149,
      "performanceTestStatus": "FINISH"
    },
    {
      "databaseType": "mssql",
      "executionTime": 476,
      "performanceTestStatus": "FINISH"
    }
  ]
}
```

##To improve:
1. Corner cases handling
2. Exception handling
3. Query validation
4. Versioning workflow API