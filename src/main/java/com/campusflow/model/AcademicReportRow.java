package com.campusflow.model;

public class AcademicReportRow {

    private String subjectCode;
    private String subjectName;

    private double marks;
    private double maxMarks;
    private int credits;

    private String letterGrade;
    private double gradePoint;

    private double attendancePercentage;

    public AcademicReportRow(
            String subjectCode,
            String subjectName,
            double marks,
            double maxMarks,
            int credits,
            String letterGrade,
            double gradePoint,
            double attendancePercentage) {

        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.credits = credits;
        this.letterGrade = letterGrade;
        this.gradePoint = gradePoint;
        this.attendancePercentage =
                attendancePercentage;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
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

    public String getLetterGrade() {
        return letterGrade;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    @Override
    public String toString() {
        return subjectCode +
                " - " +
                subjectName;
    }
}