# Todo-IT

Todo-IT is a full-stack Single Page Application (SPA) designed to help users efficiently manage their todo lists and tasks. It provides a seamless user experience with an intuitive interface and robust back-end support.

## Features
- User registration and login via email with secure authentication.
- CRUD operations for managing todo lists.
- Add tasks to lists and perform CRUD operations on tasks.
- Responsive user interface built with modern front-end technologies.
- Single Page Application (SPA) for a smooth navigation experience.

## Technologies

### Front-End
- Frameworks & Libraries: React, React Router
- Styling: TailwindCSS
- Build Tool: Vite

### Back-End
- Frameworks: Spring Boot
- Modules:
  - Spring Data JPA
  - Spring Web
  - Spring Security
  - JWT for authentication
  - JUnit and Mockito for testing

### API Communication

- RESTful API with secure JWT-based authentication.
- CSRF token for enhanced security.
- DTO pattern for structured data transfer.

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/AhmedShaaban11/todo-it.git
    ```
2. Add the dbms driver maven dependency to backend/pom.xml (if dbms isn't postgres)
3. Fill the missing fields in backend/src/main/resources/application.properties file.
    ```bash
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.datasource.driver-class-name=
    ```
4. Run the bat file
    ```bash
    ./run.bat
    ```

## Usage

1. Launch the application through the provided run.bat file.
2. Register a new account or log in with an existing account.
3. Create, update, and manage your todo lists and tasks effortlessly.