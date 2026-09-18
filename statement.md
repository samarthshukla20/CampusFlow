# CampusFlow — Smart Campus Management System

## 1. Problem Statement

Managing student academic information in a campus environment can involve
multiple disconnected processes such as student records, faculty information,
subjects, attendance, grades, and academic reports.

CampusFlow is designed to provide a centralized Java-based system for
managing these academic activities through a structured and modular
application.

The system allows authorized users to manage academic data while providing
students and other authenticated users with access to relevant academic
information.

---

## 2. Project Scope

The scope of CampusFlow includes:

- Student information management
- Faculty information management
- Subject management
- Attendance management
- Grade and academic performance management
- Academic report generation
- User authentication
- Role-based authorization
- Data validation
- Persistent storage using MySQL

The project focuses on demonstrating object-oriented programming,
database connectivity, modular software architecture, exception handling,
authentication, authorization, and testing using Java.

---

## 3. Target Users

### Administrator

The administrator manages the major academic entities in the system,
including:

- Students
- Faculty
- Subjects

The administrator can also access attendance, grades, and academic reports.

### Faculty

Faculty users can:

- Mark attendance
- Manage academic grades
- View relevant academic information
- Access academic reports

### Students

Students can access academic information available through the system,
including academic reports and performance-related information.

---

## 4. High-Level Features

### 4.1 Student Management

Provides operations for:

- Adding students
- Updating student information
- Deleting students
- Searching students
- Viewing student records

### 4.2 Faculty Management

Provides operations for:

- Adding faculty
- Updating faculty information
- Deleting faculty
- Searching faculty
- Viewing faculty records

### 4.3 Subject Management

Provides operations for:

- Adding subjects
- Updating subjects
- Deleting subjects
- Searching subjects
- Assigning faculty to subjects

### 4.4 Attendance Management

Provides functionality to:

- Mark student attendance
- View attendance records
- Calculate attendance percentage
- Identify attendance below the 75% threshold

### 4.5 Grade Management

Provides functionality to:

- Add grades
- Update grades
- Delete grades
- View student grades
- Calculate subject percentage
- Calculate letter grades
- Calculate grade points
- Calculate credit-weighted GPA

### 4.6 Academic Reports

The academic reporting module combines academic performance and attendance
information into a consolidated report.

The report provides:

- Subject-wise marks
- Percentage
- Letter grade
- Grade point
- Attendance percentage
- Overall percentage
- GPA
- Average attendance
- Low-attendance indication

### 4.7 Authentication and Authorization

CampusFlow provides user authentication and role-based authorization.

The supported roles are:

- ADMIN
- FACULTY
- STUDENT

Access to management operations is controlled according to the user's role.

---

## 5. Technology Used

- Java 17
- JavaFX
- Maven
- MySQL
- JDBC
- JUnit 5
- IntelliJ IDEA

---

## 6. Expected Outcome

The expected outcome is a functional and modular campus management
application that demonstrates practical application of Java programming,
object-oriented design, database integration, authentication,
authorization, exception handling, and software testing.