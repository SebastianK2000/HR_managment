# HR_managment


## Running the server and application:

## Description: 
The HR System project is a client-server application that allows the management of data employees via a client GUI (HRClient). The server (HRServer) handles requests from clients, passing them to the appropriate client handler (ClientHandler). The data is stored in a database using Hibernate. In the project I use tools such as InteliiJ, PostgreSQL, Hibernate, Swing, PgAdmin.

## Query to DB:

CREATE DATABASE HRDatabase;

\c HRDatabase;
 
 CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(15),
    hire_date DAT,
    salary NUMERIC(10, 2,
    job_id INTEGER,
    department_id INTEGER
 );
 
 CREATE TABLE job_history (
    employee_id INTEGER,,
    start_date DATE,
    end_date DATE,
    job_id INTEGER,
    department_id INTEGER,
    PRIMARY KEY (employee_id, start_date)
 );
 
 CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
 );
 
 CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role_id INTEGER REFERENCES roles(id)
    );
    
 ALTER TABLE employees
 ADD CONSTRAINT fk_job
 FOREIGN KEY (job_id)
 REFERENCES job_history(job_id);
 ALTER TABLE employees
 ADD CONSTRAINT fk_department
 FOREIGN KEY (department_id)
 REFERENCES job_history(department_id);

## Architecture:

### Client (HRClient)

A class representing the user interface for the HR System.

Functions:

Adding an employee: Supports adding a new employee to the system. Deleting an employee: Deletes an employee based on ID. Update employee data: Updates the data of an existing employee. Displaying all employees: Displays a list of all employees in the 
system.

### Server (HRServer): 

A class responsible for listening for calls from clients and forwarding them to the appropriate handlers.

Functions:

Call listening: Waits for calls from clients on the specified port. Running threads for clients: Creates handler threads for each new connection.

### Client Handler (ClientHandler)
 
A class that handles requests from clients and processes them according to the type of operation (add, delete, update, read).

Functions:

JSON request processing: Accepts and processes client requests in JSON format. 


CRUD operation support: it supports operations for adding, deleting, updating and 
reading of employee data.

### Hibernate Configuration (HibernateUtil).

A class used to configure and manage Hibernate sessions.

Functions:
Configure SessionFactory: Initializes the SessionFactory for Hibernate. Session Closure: Manages the lifecycle of a Hibernate session.
 
### EmployeeService
 
A class that manages business operations related to employees.

Functions:
Employee Enrollment: Adds a new employee to the database. Retrieve all employees: Returns a list of all employees. Retrieving employee by ID: Finds an employee based on the specified ID. Updating employee data: Updates an existing employee in the database. Deleting an employee: Removes an employee from the database based on the ID.
 
### Employee DAO (EmployeeDAO).
 
Data Access Object (DAO) class for the employee entity.

Functions:
Employee enrollment: Saves a new employee to the database. Retrieve all employees: Returns a list of all employees from the database. Retrieving employee by ID: Finds an employee based on the specified ID. Updating employee data: Updates the data of an existing employee in the database. Deleting employee: Removes an employee from the database based on the ID.

### Screens: 

![image](https://github.com/SebastianK2000/HR_managment/assets/127401994/19a75874-3a56-4505-8aa8-58dcaf42556d)

![image](https://github.com/SebastianK2000/HR_managment/assets/127401994/f83c0675-01a0-4616-88a8-66a2f63a792d)
