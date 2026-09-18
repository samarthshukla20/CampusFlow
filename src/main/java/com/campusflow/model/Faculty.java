package com.campusflow.model;

public class Faculty {

    private int id;
    private int userId;
    private String name;
    private String email;
    private String employeeId;
    private String department;

    public Faculty(
            int id,
            int userId,
            String name,
            String email,
            String employeeId,
            String department) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.employeeId = employeeId;
        this.department = department;
    }

    public Faculty(
            String name,
            String email,
            String employeeId,
            String department) {

        this(
                0,
                0,
                name,
                email,
                employeeId,
                department
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

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return employeeId + " - " + name;
    }
}