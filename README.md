# CampusFlow

## Smart Campus Management System

CampusFlow is a Java-based campus management system designed to centralize
student, faculty, subject, attendance, grade, and academic report management
within a single application.

The project demonstrates practical implementation of Java programming,
object-oriented design, database connectivity, authentication,
role-based authorization, exception handling, and automated testing.

---

## Features

### Student Management

- Add student records
- Update student information
- Delete students
- Search students
- View all students

### Faculty Management

- Add faculty records
- Update faculty information
- Delete faculty
- Search faculty
- View all faculty members

### Subject Management

- Add subjects
- Update subjects
- Delete subjects
- Search subjects
- Assign faculty to subjects

### Attendance Management

- Mark student attendance
- View attendance records
- Calculate attendance percentage
- Identify students with attendance below 75%

### Grade Management

- Add grades
- Update grades
- Delete grades
- View student grades
- Calculate subject percentage
- Generate letter grades
- Calculate grade points
- Calculate credit-weighted GPA

### Academic Reports

The reporting module combines academic performance and attendance
information into a consolidated report.

It provides:

- Subject-wise marks
- Percentage
- Letter grade
- Grade point
- Attendance percentage
- Overall percentage
- GPA
- Average attendance
- Low-attendance indication

### Authentication & Authorization

CampusFlow supports role-based access using three roles:

| Role | Access |
|------|--------|
| ADMIN | Student, faculty, subject, attendance, grades and reports |
| FACULTY | Attendance, grades and reports |
| STUDENT | Academic information and reports |

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Core application development |
| JavaFX | Graphical user interface |
| Maven | Build and dependency management |
| MySQL | Relational database |
| JDBC | Database connectivity |
| JUnit 5 | Unit testing |
| IntelliJ IDEA | Development environment |

---

## Architecture

CampusFlow follows a layered architecture:

```text
┌───────────────────────────────┐
│          JavaFX UI            │
│     Presentation Layer        │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          Services             │
│ Business Logic & Validation   │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│        Repositories           │
│       JDBC / SQL Layer        │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│            MySQL              │
│       CampusFlow Database     │
└───────────────────────────────┘
```

Supporting layers include:

- Model classes for application data
- Security classes for roles and password handling
- Exception classes for error handling
- Utility classes for reusable functionality

---

## Project Structure

```text
CampusFlow
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.campusflow
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       ├── ui
│   │   │       ├── util
│   │   │       ├── CampusFlowApplication.java
│   │   │       └── Main.java
│   │   │
│   │   └── resources
│   │
│   └── test
│       └── java
│           └── com.campusflow
│
├── pom.xml
├── statement.md
├── README.md
└── .gitignore
```

---

## Database

CampusFlow uses MySQL for persistent data storage.

The database contains the following major entities:

- Users
- Students
- Faculty
- Subjects
- Attendance
- Grades

Relationships between these entities are maintained using primary keys,
foreign keys, unique constraints, and database-level validation.

---

## Database Setup

Create the database using MySQL:

```sql
CREATE DATABASE campusflow;
```

Then execute the project's database schema to create the required tables.

The application expects the MySQL database to be available at:

```text
localhost:3306/campusflow
```

---

## Database Configuration

CampusFlow does not store database credentials directly in the source code.

Configure the following environment variables:

```text
CAMPUSFLOW_DB_USER
CAMPUSFLOW_DB_PASSWORD
```

For example:

```text
CAMPUSFLOW_DB_USER=root
CAMPUSFLOW_DB_PASSWORD=your_password
```

Do not commit actual database credentials to GitHub.

---

## Requirements

Before running the project, install:

- JDK 17 or later
- Maven
- MySQL Server
- IntelliJ IDEA or another Java IDE supporting Maven and JavaFX

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/samarthshukla20/CampusFlow.git
```

### 2. Open the project

Open the project in IntelliJ IDEA as a Maven project.

### 3. Configure MySQL

Create the `campusflow` database and execute the database schema.

### 4. Configure environment variables

Set:

```text
CAMPUSFLOW_DB_USER
CAMPUSFLOW_DB_PASSWORD
```

### 5. Reload Maven

Allow IntelliJ IDEA to download the required Maven dependencies.

### 6. Run the application

Run:

```text
com.campusflow.CampusFlowApplication
```

using the configured JavaFX/Maven run configuration.

---

## Testing

CampusFlow uses JUnit 5 for service-layer testing.

The project includes tests covering:

- Student service
- Faculty service
- Subject service
- Attendance service
- Grade service
- Authentication service
- Authorization service
- Academic report service

The tests cover validation, business logic, calculations, authentication,
authorization, and other service-level behaviour.

Run the tests using IntelliJ IDEA or Maven:

```bash
mvn test
```

---

## Security

The application includes:

- Role-based authorization
- Password hashing
- Input validation
- Prepared SQL statements
- Database constraints
- Exception handling
- Environment-based database credentials

Database credentials are intentionally kept outside the source code.

---

## Academic Calculations

### Attendance

```text
Attendance Percentage =
(Present Classes / Total Classes) × 100
```

The application identifies attendance below:

```text
75%
```

as low attendance.

### Subject Percentage

```text
Percentage =
(Marks / Maximum Marks) × 100
```

### GPA

CampusFlow calculates GPA using credit-weighted grade points:

```text
GPA =
Σ(Grade Point × Credits) / Σ(Credits)
```

### Grade Scale

| Percentage | Grade | Grade Point |
|------------|-------|-------------|
| 90–100 | A+ | 10 |
| 80–89 | A | 9 |
| 70–79 | B | 8 |
| 60–69 | C | 7 |
| 50–59 | D | 6 |
| 40–49 | E | 5 |
| Below 40 | F | 0 |

---

## Design Principles

The project follows several software engineering principles:

- Separation of concerns
- Layered architecture
- Object-oriented programming
- Encapsulation
- Modular design
- Input validation
- Exception handling
- Database normalization
- Prepared SQL statements
- Role-based access control

---

## Project Status

**Status: Completed**

CampusFlow currently includes the core campus management modules,
authentication and authorization, database integration, JavaFX interface,
academic calculations, reporting, and automated service-level tests.

---

## Author

**Samarth Shukla**

B.Tech Computer Science and Engineering
Specialization: Artificial Intelligence & Machine Learning

---

## License

This project was developed as an academic project for educational purposes.
