package com.campusflow;

import com.campusflow.model.AcademicReportRow;
import com.campusflow.service.AcademicReportService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AcademicReportServiceTest {

    private final AcademicReportService service =
            new AcademicReportService();

    @Test
    void shouldRejectInvalidStudentId() {
        assertThrows(
                RuntimeException.class,
                () -> service.getReportForStudent(0)
        );
    }

    @Test
    void shouldReturnZeroAveragePercentageForEmptyList() {
        assertEquals(
                0.0,
                service.calculateAveragePercentage(List.of()),
                0.001
        );
    }

    @Test
    void shouldCalculateAveragePercentage() {
        AcademicReportRow row1 =
                new AcademicReportRow(
                        "CS101",
                        "Java",
                        80,
                        100,
                        4,
                        "A",
                        9,
                        90
                );

        AcademicReportRow row2 =
                new AcademicReportRow(
                        "CS102",
                        "Database",
                        70,
                        100,
                        2,
                        "B",
                        8,
                        80
                );

        assertEquals(
                75.0,
                service.calculateAveragePercentage(
                        List.of(row1, row2)
                ),
                0.001
        );
    }

    @Test
    void shouldCalculateAverageAttendance() {
        AcademicReportRow row1 =
                new AcademicReportRow(
                        "CS101",
                        "Java",
                        80,
                        100,
                        4,
                        "A",
                        9,
                        90
                );

        AcademicReportRow row2 =
                new AcademicReportRow(
                        "CS102",
                        "Database",
                        70,
                        100,
                        2,
                        "B",
                        8,
                        60
                );

        assertEquals(
                75.0,
                service.calculateAverageAttendance(
                        List.of(row1, row2)
                ),
                0.001
        );
    }

    @Test
    void shouldCalculateCreditWeightedGPA() {
        AcademicReportRow row1 =
                new AcademicReportRow(
                        "CS101",
                        "Java",
                        80,
                        100,
                        4,
                        "A",
                        9,
                        90
                );

        AcademicReportRow row2 =
                new AcademicReportRow(
                        "CS102",
                        "Database",
                        70,
                        100,
                        2,
                        "B",
                        8,
                        80
                );

        /*
         * GPA =
         * ((9 × 4) + (8 × 2)) / (4 + 2)
         * = 52 / 6
         * = 8.6667
         */

        assertEquals(
                8.6667,
                service.calculateGPA(
                        List.of(row1, row2)
                ),
                0.001
        );
    }

    @Test
    void shouldReturnZeroGPAForEmptyList() {
        assertEquals(
                0.0,
                service.calculateGPA(List.of()),
                0.001
        );
    }

    @Test
    void shouldDetectLowAttendance() {
        AcademicReportRow row =
                new AcademicReportRow(
                        "CS101",
                        "Java",
                        80,
                        100,
                        4,
                        "A",
                        9,
                        70
                );

        assertTrue(
                service.hasLowAttendance(row)
        );
    }

    @Test
    void shouldNotDetectLowAttendanceAtThreshold() {
        AcademicReportRow row =
                new AcademicReportRow(
                        "CS101",
                        "Java",
                        80,
                        100,
                        4,
                        "A",
                        9,
                        75
                );

        assertFalse(
                service.hasLowAttendance(row)
        );
    }

    @Test
    void shouldReturnAttendanceThreshold() {
        assertEquals(
                75.0,
                service.getAttendanceThreshold(),
                0.001
        );
    }
}