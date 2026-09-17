package com.campusflow.model;

public class Student {

    private int id;
    private int userId;
    private String name;
    private String email;
    private String enrollmentNumber;
    private String department;
    private int semester;

    // Constructor for reading an existing student
    public Student(
            int id,
            int userId,
            String name,
            String email,
            String enrollmentNumber,
            String department,
            int semester
    ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.enrollmentNumber = enrollmentNumber;
        this.department = department;
        this.semester = semester;
    }

    // Constructor for creating a new student
    public Student(
            String name,
            String email,
            String enrollmentNumber,
            String department,
            int semester
    ) {
        this(
                0,
                0,
                name,
                email,
                enrollmentNumber,
                department,
                semester
        );
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public String getDepartment() {
        return department;
    }

    public int getSemester() {
        return semester;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", department='" + department + '\'' +
                ", semester=" + semester +
                '}';
    }
}