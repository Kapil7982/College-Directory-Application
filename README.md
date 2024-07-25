# College Directory API

## Overview

The College Directory API is a comprehensive system for managing student, faculty, and administrative data for a college. It provides secure endpoints for various operations including user authentication, profile management, course administration, and departmental organization.

## Features

- User Authentication with JWT
- Role-based Access Control (Student, Faculty, Administrator)
- Student Profile Management
- Faculty Profile Management
- Course Management
- Department Management
- Admin Operations

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT for Authentication
- JPA/Hibernate
- RESTful API Design

## Getting Started

### Prerequisites

- Java 8 or later
- Maven
- Your favorite IDE (IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository: 
```bash
git clone https://github.com/Kapil7982/College-Directory-Application.git
```
2. Navigate to the project directory:
   cd collegeDirectory
   
3. Build the project:
   mvn clean install

4. Run the application

The API will start running on `http://localhost:8080`.

## API Endpoints


### Authentication

- `POST /api/auth/signIn`: Sign in and receive JWT token

### Users

- `GET /api/users`: Get all users (Admin only)
- `POST /api/users`: Create a new user (Does not require any authentication)
- `GET /api/users/{email}`: Get user by email

### Students

- `GET /api/students/profile/{userId}`: Get student profile
- `PUT /api/students/profile`: Update student profile
- `POST /api/students/profile`: Create student profile (Admin only)
- `GET /api/students/search`: Search students (Admin only)

### Faculty

- `GET /api/faculty/profile/{userId}`: Get faculty profile
- `PUT /api/faculty/profile`: Update faculty profile
- `POST /api/faculty/create`: Create faculty profile (Admin only)
- `GET /api/faculty/{facultyId}`: Get faculty classes

### Courses

- `GET /api/courses`: Get all courses
- `POST /api/courses`: Create a new course (Admin only)
- `PUT /api/courses/{id}`: Update a course (Admin only)
- `DELETE /api/courses/{id}`: Delete a course (Admin only)

### Departments

- `GET /api/departments`: Get all departments
- `POST /api/departments`: Create a new department (Admin only)
- `PUT /api/departments/{id}`: Update a department (Admin only)
- `DELETE /api/departments/{id}`: Delete a department (Admin only)

## Security

This API uses JWT (JSON Web Tokens) for authentication. Include the JWT token in the Authorization header for protected endpoints:
Authorization: Bearer <your_token_here>

## Error Handling

The API provides detailed error messages for various scenarios:

- 400 Bad Request: For invalid input
- 401 Unauthorized: For authentication failures
- 403 Forbidden: For authorization failures
- 404 Not Found: For resources that don't exist
- 500 Internal Server Error: For server-side issues
