## About
- spring boot pizzaria API
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
    "testData": false
}
```
Valid pizza order statuses: "PREPARING", "BAKED", "DELIVERING", "DELIVERED"
Valid pizza sizes: "SMALL", "MEDIUM", "LARGE"

## Requirements:
The current url for the service on AWS ECS is: http://13.58.25.163:8080/pizzaria/
This IP address could change. Please refer to this documenet for the latest IP address.

The `REST` service must expose the `/pizzaria/` endpoint, which allows for managing the collection of pizzaria pizza order records in the following way:

POST request to `/pizzaria/`:

- creates a new pizzaria data record
- expects a valid pizzaria data object as its body payload, except that it has a null id property for the order and the pizzas; you can assume that the given object is always valid
- adds the given object to the collection and assigns a unique integer id to it
- the response code is 201 and the response body is the created record, including its unique id
- Please see the example of a pizzaria order data JSON object

GET request to `/pizzaria`:

- the response code is 200
- the response body is an array of matching pizza order records, ordered by their ids in increasing order


GET request to `/pizzaria/<id>`:

- returns a pizza order record with the given id
- if the matching record exists, the response code is 200 and the response body is the matching object
- if there is no record in the collection with the given id, the response code is 404

DELETE request to `/pizzaria/<id>`:

- deletes a pizza order record with the given id
- if the matching record exists, the response code is 204 and the response body is empy
- if there is no record in the collection with the given id, the response code is 404

PUT request to `/pizzaria/status/<id>/<new status>`:

- updates a pizza order record with the given id to new status
- if the matching record exists, the response code is 200 and the response body is the updated pizza order 
- if there is no record in the collection with the given id, the response code is 404
  
## Commands
- run: 
```bash
mvn clean package; java -jar target/pizzariaApi-1.0-SNAPSHOT.jar
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```
