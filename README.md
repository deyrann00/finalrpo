# Help Desk Ticketing System

### TechCorp Enterprise Edition

A robust backend Help Desk Management System developed as a final project. The system automates internal IT support workflows and manages interactions between Employees, Support Agents, and Administrators using a secure role-based architecture.



## Features

* **User Management**: Registration, profile updates, and account blocking/unblocking.
* **Role-Based Access Control (RBAC)**: Distinct permissions for `ROLE_ADMIN`, `ROLE_SUPPORT_AGENT`, and `ROLE_USER`.
* **Ticketing Lifecycle**: Create, update, resolve, and delete support tickets.
* **Collaborative Comments**: Threaded communication between users and support staff on tickets.
* **Metadata Management**: Categorization and tagging system (Hardware, Software, Urgent, etc.).
* **Secure REST API**: Protected endpoints with 401 Unauthorized handling for REST clients.

## Architecture

The application follows a **Layered Architecture** pattern to ensure clean separation of concerns, scalability, and maintainability.

* **Controller Layer**: Handles HTTP requests, RequestParams, and PathVariables.
* **Service Layer**: Orchestrates business logic (e.g., password hashing, status transitions).
* **Repository Layer**: Manages database persistence using Spring Data JPA.
* **Security Layer**: Implements RBAC and Password Encoding using Spring Security.
* **DTO/Mapper Layer**: Uses MapStruct to prevent exposing internal Entities to the API.



## Getting Started

### Prerequisites

* **Java 17** or higher
* **PostgreSQL** (running locally)
* **Gradle** (built-in wrapper included)

### Database Setup

1.  Create an empty PostgreSQL database named: `finalpro`
2.  Configure your credentials in: `src/main/resources/application.properties`

### Database Migrations

**Liquibase** is used for database version control. Upon startup, the system automatically:
* Creates the schema (`t_users`, `t_tickets`, `t_comments`, etc.).
* Initializes reference data (Departments, Roles, Categories).
* Configures foreign keys with `ON DELETE CASCADE` for data integrity.

## Running the Application

1.  Clone the repository.
2.  Run the application using IntelliJ IDEA or the terminal:
    ```bash
    ./gradlew bootRun
    ```
3.  The server will start on `http://localhost:8080`.

## API Documentation & Testing

A complete Postman collection is provided: `Final RPO Collection.postman_collection.json`.

The collection is organized into folders for a **Full CRUD Demonstration**:
1.  **Authentication**: Sign-up, Sign-in, and Password Change.
2.  **Admin - User CRUD**: Create, Update, Block, and Delete users.
3.  **Tickets**: Status updates (`/status?status=RESOLVED`) and creation.
4.  **Comments**: Post-migration communication logs.



## Technology Stack

* **Backend**: Java 17, Spring Boot 3.4.1
* **Security**: Spring Security (BCrypt, RBAC)
* **ORM**: Spring Data JPA / Hibernate 7
* **Database**: PostgreSQL
* **Migrations**: Liquibase
* **Mapping**: MapStruct & Lombok



## Project Purpose

This project demonstrates proficiency in:
* Implementing a secure **RESTful API**.
* Managing complex **Database Relationships** (Many-to-Many for Tags, One-to-Many for Comments).
* Handling **Data Integrity** and constraints in a multi-role environment.
* Separating API models (DTOs) from Data models (Entities).
