package com.campusflow.model;

public class Grade {

    private int id;
    private int studentId;
    private int subjectId;
    private double marks;
    private double maxMarks;
    private int credits;

    public Grade(int id, int studentId, int subjectId,
                 double marks, double maxMarks, int credits) {

        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.credits = credits;
    }

    // Constructor for creating a new grade
    public Grade(int studentId, int subjectId,
                 double marks, double maxMarks, int credits) {

        this(0, studentId, subjectId, marks, maxMarks, credits);
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public double getMarks() {
        return marks;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public int getCredits() {
        return credits;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", marks=" + marks +
                ", maxMarks=" + maxMarks +
                ", credits=" + credits +
                '}';
    }
}