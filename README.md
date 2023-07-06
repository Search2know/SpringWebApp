# CRUD Application with MongoDB and Docker Compose

This is a simple CRUD (Create, Read, Update, Delete) application implemented in Java using MongoDB as the database. The application can be easily deployed using Docker Compose, which simplifies the setup and configuration of the required services.

## Prerequisites

* Java Development Kit (JDK) 8 or higher
* Docker
* Docker Compose

## Getting Started

1. Clone the repository or download the source code.
2. Ensure that Java and Docker are properly installed on your system.
3. Open a command prompt or terminal.
4. Navigate to the project directory.

## Configuration

Before running the application, make sure to configure the MongoDB connection settings in the application.properties file. The file should contain the following properties:
 ``` properties
     spring.data.mongodb.host=mongodb
     spring.data.mongodb.port=27017
     spring.data.mongodb.database=<mongodb-database>
 ```

## Usage

To use the application with Docker Compose, follow these steps:

1. Build the project:
 ```  shell
 ./mvnw clean package
```

2. Start the application and MongoDB using Docker Compose:
3. Use the following commands:
    * Create a new file:
      ``` 
      create file [text to add]
      ```
      This command creates a new file with the provided text description.

    * Display existing files:
      ```
      show
       ```
      This command displays a list of all the files along with their positions.
   * Update a file description:
     ```
     update [position of file] [text description updating]
     ``` 
     This command updates the description of the file at the specified position.
   * Delete a file:
     ``` 
     delete [position of file]
     ``` 
     This command deletes the file at the specified position.
   * Exit the application:
     ``` 
     exit
     ``` 
     This command exits the application.

## Notes 
* The position of the file refers to its index in the list of files displayed by the show command.
* Text descriptions can contain spaces, alphanumeric characters, and punctuation.
* Make sure to provide valid inputs as per the instructions provided for each command.