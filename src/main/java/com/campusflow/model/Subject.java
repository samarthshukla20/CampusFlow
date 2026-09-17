package com.campusflow.model;

public class Subject {

    private int id;
    private String subjectCode;
    private String subjectName;
    private Integer facultyId;

    public Subject(
            int id,
            String subjectCode,
            String subjectName,
            Integer facultyId
    ) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.facultyId = facultyId;
    }

    public Subject(
            String subjectCode,
            String subjectName,
            Integer facultyId
    ) {
        this(
                0,
                subjectCode,
                subjectName,
                facultyId
        );
    }

    public int getId() {
        return id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", facultyId=" + facultyId +
                '}';
    }
}