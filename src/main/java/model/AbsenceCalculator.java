package model;

import java.util.List;
import java.util.Set;

public class AbsenceCalculator {

    public void calculateAbsences(List<StudentAttendance> attendanceRecords, Set<String> generatedTimeInCodes,
            Set<String> generatedTimeOutCodes) {
        for (StudentAttendance record : attendanceRecords) {
            int absences = 0;

            // Counting absences for TimeINCode
            if (record.getTimeInCode() == null || !generatedTimeInCodes.contains(record.getTimeInCode())) {
                absences++;
            }

            // Counting absences for TimeOUTCode
            if (record.getTimeOutCode() == null || !generatedTimeOutCodes.contains(record.getTimeOutCode())) {
                absences++;
            }

            // Setting the total absences for the record
            record.setAbsences(absences);
        }
    }
}
