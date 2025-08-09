# Todo-IT

Todo-IT is a full-stack Single Page Application (SPA) designed to help users efficiently manage their todo lists and tasks. It provides a seamless user experience with an intuitive interface and robust back-end support.

## Overview

Checkout this video for a quick overview of the application: [Todo-IT Overview](https://drive.google.com/file/d/1NQ9RvoRxwiQ9K9XQG-cNK92EQp51m53z/view?usp=drive_link)

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

2. Create a database for the application (e.g., PostgreSQL, MySQL, etc.) and note down the jdbc connection details (e.g., jdbc:postgresql://localhost:5432/todo).

3. Set up environment variables (for Windows):
    - Create a `.env.bat` file in the `bin` directory with the following content (replace placeholders with your actual values):

    ```bat
    SET FRONTEND_PORT=[your frontend port]
    SET BACKEND_PORT=[your backend port]
    SET DB_USERNAME=[your database username]
    SET DB_PASSWORD=[your database password]
    SET DB_URL=[jdbc URL for your database]
    ```

    - For Linux or macOS, create a `.env.sh` file in the `bin` directory with similar content:

    ```bash
    export FRONTEND_PORT=[your frontend port]
    export BACKEND_PORT=[your backend port]
    export DB_USERNAME=[your database username]
    export DB_PASSWORD=[your database password]
    export DB_URL=[jdbc URL for your database. Example: jdbc:postgresql://localhost:5432/todo]
    ```

## Usage

1. Launch the application through the provided `bin\run.bat` (Windows) or `bin/run.sh` (Linux/macOS) file.
2. Register a new account or log in with an existing account.
3. Create, update, and manage your todo lists and tasks effortlessly.
4. Enjoy the responsive and user-friendly interface.

## Notes

- The database will be automatically created with the necessary tables when the application starts, then it will be dropped when the application stops.
