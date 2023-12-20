package model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbsenceCalculator {

    public void calculateAbsences(List<StudentAttendance> attendanceRecords) {
        Map<String, Long> timeInCodeUsage = attendanceRecords.stream()
                .collect(Collectors.groupingBy(StudentAttendance::getTimeInCode, Collectors.counting()));

        Map<String, Long> timeOutCodeUsage = attendanceRecords.stream()
                .collect(Collectors.groupingBy(StudentAttendance::getTimeOutCode, Collectors.counting()));

        for (StudentAttendance record : attendanceRecords) {
            if (timeInCodeUsage.getOrDefault(record.getTimeInCode(), 0L) > 1 ||
                    !record.getGeneratedTimeInCode().contains(record.getTimeInCode())) {
                record.incrementAbsence();
            }

            if (timeOutCodeUsage.getOrDefault(record.getTimeOutCode(), 0L) > 1 ||
                    !record.getGeneratedTimeOutCode().contains(record.getTimeOutCode())) {
                record.incrementAbsence();
            }
        }
    }
}
