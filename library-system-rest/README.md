# Library System REST API

## Build & run

Run `mvn clean install` from the root project directory.
Run `mvn tomcat7:run` from the [library-system-rest](.) submodule.

The REST API is available at <http://localhost:8080/pa165/rest>.

## Book entity operations

### GET

To get all the available books:

`curl -i -X GET http://localhost:8080/pa165/rest/books`

To get the book with the specified identifier:

`curl -i -X GET http://localhost:8080/pa165/rest/books/{id}`

### DELETE

To delete the book with specified identifier:

`curl -i -X DELETE http://localhost:8080/pa165/rest/books/{id}`

### POST

To create new book:

`curl -X POST -i -H "Content-Type: application/json" --data '{"title":"Title","author":"Author","isbn":"978"}' http://localhost:8080/pa165/rest/books`

### PUT

To update the existing book:

`curl -X PUT -i -H "Content-Type: application/json" --data '{"author":"New Author"}' http://localhost:8080/pa165/rest/books/{id}`
