Prerequisites
Before you begin, ensure you have the following installed:

Java JDK 20 or newer
PostgreSQL 16 or newer
Maven

Navigate to src/main/resources/ and open the application.properties file. Set your PostgreSQL database connection properties:
All the Datasource Config info is in the (application.properties) file.

The app is using Hibernate so No need for a SQL script.

Our APIs uses Basic Auth for authentication.
To interact with any secured endpoints, you must include a valid Basic Auth in the Authorization header of your HTTP request.
The app contains 2 user Roles 1-USER  2 -ADMIN
The user Role has access to GET methods only. 
while Admin has access to all endpoints.(You can check SecurityConfig class inside the Configuration package)
The user role credentials are {"username": "user","password": "123"} 
while the admin role credentials are {"username": "user","password": "123"}


Using the API:
Once the application is running, you can access the following endpoints through a tool like Postman

POST: /api/books: Add a new book to the library. with body {"title":"1984","author": "Goerge Orwell","publicationYear": "1980","isbn": "58478-sc"}
POST: /api/patrons: Add a new patron to the library. with body {"name":"Omar Emad Bidaq","contactInfo": "Cairo"}
GET /api/books: Retrieve a list of all books.
GET /api/books/1: Retrieve a a book with ID.
GET /api/patrons: Retrieve a list of all patrons.
GET /api/patrons/1: Retrieve a patron with ID.
POST /api/borrow/1/patron/1: Allow a patron to borrow a book.
PUT /api/return/1/patron/1: Record the return of a borrowed book by a patron.
PUT /api/books/1: Update a a book with ID.
PUT /api/patrons/1: Update a a patron with ID.
Delete /api/books/1: Delete a a book with ID.
Delete /api/patrons/1: Delete a a patron with ID.
