## About
- spring boot pizzaria API exam
## Environment:
- Java version: 21
- Maven version: 3.*
- Spring Boot version: 3.3.4

## Read-Only Files:
- src/test/*

## Data:
Example of a pizzaria data JSON object:
```
{
    "id": null,
    "dateTime": "2024-10-25T09:47:53.4094317",
    "customerName": "Tom",
    "customerAddress": "248 State Av.",
    "customerPhoneNumber": "9876543210",
    "status": "PREPARING",
    "pizzaList": [
        {
            "id": null,
            "type": "REGULAR",
            "size": "LARGE",
            "topping": "MEAT",
            "price": 10.59,
            "quantity": 0
        },
        {
            "id": null,
            "type": "REGULAR",
            "size": "SMALL",
            "topping": "PEPERONI",
            "price": 8.59,
            "quantity": 0
        }
    ],
    "testData": true
}
```

## Requirements:
The `REST` service must expose the `/pizzaria` endpoint, which allows for managing the collection of pizzaria records in the following way:


POST request to `/pizzaria`:

- creates a new pizzaria data record
- expects a valid pizzaria data object as its body payload, except that it does not have an id property; you can assume that the given object is always valid
- adds the given object to the collection and assigns a unique integer id to it
- the response code is 201 and the response body is the created record, including its unique id


GET request to `/pizzaria`:

- the response code is 200
- the response body is an array of matching records, ordered by their ids in increasing order
- accepts an optional query string parameter, date, in the format YYYY-MM-DD, for example /pizzaria/?date=2019-06-11. When this parameter is present, only the records with the matching date are returned.
- accepts an optional query string parameter, city, and when this parameter is present, only the records with the matching city are returned. The value of this parameter is case insensitive, so "London" and "london" are equivalent. Moreover, it might contain several values, separated by commas (e.g. city=london,Moscow), meaning that records with the city matching any of these values must be returned.
- accepts an optional query string parameter, sort, that can take one of two values: either "date" or "-date". If the value is "date", then the ordering is by date in ascending order. If it is "-date", then the ordering is by date in descending order. If there are two records with the same date, the one with the smaller id must come first.


GET request to `/pizzaria/<id>`:

- returns a record with the given id
- if the matching record exists, the response code is 200 and the response body is the matching object
- if there is no record in the collection with the given id, the response code is 404

## Commands
- run: 
```bash
mvn clean package; java -jar target/orderinApi-1.0-SNAPSHOT.jar
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```
