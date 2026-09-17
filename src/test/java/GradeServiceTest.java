package com.campusflow;

import com.campusflow.exception.ValidationException;
import com.campusflow.model.Grade;
import com.campusflow.service.GradeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeServiceTest {

    private final GradeService gradeService =
            new GradeService();

    @Test
    void shouldCalculatePercentageCorrectly() {

        Grade grade = new Grade(
                1,
                1,
                85,
                100,
                4
        );

        double percentage =
                gradeService.calculatePercentage(grade);

        assertEquals(85.0, percentage);
    }

    @Test
    void shouldCalculateLetterGradeCorrectly() {

        Grade grade = new Grade(
                1,
                1,
                85,
                100,
                4
        );

        String letterGrade =
                gradeService.calculateLetterGrade(grade);

        assertEquals("A", letterGrade);
    }

    @Test
    void shouldCalculateGradePointCorrectly() {

        Grade grade = new Grade(
                1,
                1,
                85,
                100,
                4
        );

        double gradePoint =
                gradeService.calculateGradePoint(grade);

        assertEquals(9.0, gradePoint);
    }

    @Test
    void shouldRejectNegativeMarks() {

        Grade grade = new Grade(
                1,
                1,
                -10,
                100,
                4
        );

        assertThrows(
                ValidationException.class,
                () -> gradeService.calculatePercentage(grade)
        );
    }

    @Test
    void shouldRejectMarksGreaterThanMaximum() {

        Grade grade = new Grade(
                1,
                1,
                110,
                100,
                4
        );

        assertThrows(
                ValidationException.class,
                () -> gradeService.calculatePercentage(grade)
        );
    }

    @Test
    void shouldRejectInvalidCredits() {

        Grade grade = new Grade(
                1,
                1,
                80,
                100,
                0
        );

        assertThrows(
                ValidationException.class,
                () -> gradeService.calculatePercentage(grade)
        );
    }
}