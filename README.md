# SMDLIB Simple Library Management System


Tutorial of how to run and test the program can be found in the ''

## Project Overview:
v podstate o com to je

## Technology Stack:
In our project, we have 3 layers with RESTfulAPI between the first 2:
1. **JavaFX client** - dopis
2. **Spring Boot** - server side Java framework for proccessing requests and security
3. **SQLite database** - to be specific, we choose SQLite3 and it is not a in-memory database, so after server restart we won't loose our stored data

As said, between layers **1** and **2** we implemented **RESTful API** to handle requests from the client using http methods (CRUD)

## Classes and Functions:
The server has 5 main types of classes based on what they do. They are also grouped up into packages.
1. **Model** - It is a Spring Boot object, usually a record for containing for example a user, book, transaction. So basically a certain instance of a object and the model class the blueprint of how should the instance look like.
2. **Repository** - The repository (repo) is responsible for interacting with the SQLite database and either retrieve or manage data. Here lies the logic behind data interaction.
3. **Controller** - Controllers are the "dumb" classes, because they do nothing but exchange data between their endpoints and either the repositories or clients. Here are the CRUD functions implemented.
4. **Security** - Here we put the SecurityConfig, JWT files and other security related classes. AuthConroll is the only security class that is not here but in the Controllers package, because it has endpoints login and register.
5. **DTO** - Data Transfer Objects, are helpful classes that can carry the data to/from the server and client, we used it only to transfer data to/from Endpoints. Also, it is more convenient to send and proccess one object rather than multiple attributes of our models.

Also, out main class for the server is separated from these packages. Same goes for the database.

## RESTful API:


## Database Design:
The database file can be found next to the server src/ folder at Database/LIBDB.db with the database controller Classes
We have 4 tables to work with:

**Users**: Contains all users, including admins, has all the info you need about users
**Books**: The library of our database, the books and their info are here
**Borrows**: A logging table, which just contains the history of user made borrows and returns of books
**Roles**: A help table to more easily determine who is the admin and who is the user

## User Interface: to si dopln

## Challenges and Approach:
### Main Challenges
1. we have to implent SERVER-CLIENT architecture
2. implement RESTfulAPI and its CRUD functions
3. setup a SQLite database as our main storage
4. secure our server with roles and register/login
5. give the users a UI Client to communicate with our server

### Our approuch
1. using Spring Boot as our server framework, we easily achieved this goal
2. Spring boot offers http methods like GET, POST, PUT and DELETE to achieve CRUD
3. we connected to our database with JDBC and manage it via Java on the server
4. secure our server with Spring Boot Security using a JWT to keep users logged in
5. our UI is made with JavaFX Spring Boot client so users can interact with the server