# Student Management System — Full Stack Java

A CRUD web application built with Java 17, Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf, HTML, CSS, and a database. It supports creating, listing, searching, editing, and deleting student records.

## Requirements
- Java 17 or newer
- Maven 3.8+ (or use the Maven support in your IDE)
- Internet connection the first time Maven downloads dependencies

## Run quickly (H2 in-memory database)
1. Extract the ZIP.
2. Open a terminal in the `student-management-system` folder.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Open http://localhost:8080

The default H2 database is in-memory, so data is reset when the application stops. You do not need to install MySQL for the default configuration.

## Use MySQL instead
1. Create a database:
   ```sql
   CREATE DATABASE student_management;
   ```
2. In `src/main/resources/application.properties`, comment out the H2 datasource settings and uncomment the MySQL settings.
3. Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.
4. Run `mvn spring-boot:run` again.

## Features
- Add student (name, email, course)
- View student directory
- Search by name, email, or course
- Edit student details
- Delete student records with confirmation
- Validation for required fields, email format, and duplicate email
- Responsive UI

## Project structure
```
src/main/java/com/example/studentmanagement/
  StudentManagementApplication.java
  controller/StudentController.java
  model/Student.java
  repository/StudentRepository.java
src/main/resources/
  application.properties
  templates/students/list.html
  templates/students/form.html
  static/css/style.css
```

## Notes
This is a learning/demo project, not a production-ready system. Before production use, add authentication/authorization, stronger error handling, database migrations, and automated tests.
