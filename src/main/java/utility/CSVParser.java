package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.StudentAttendance;

public class CSVParser {

    public List<StudentAttendance> parseCSV(String filePath) {
        List<StudentAttendance> attendanceRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Parse the columns
                String studentNumber = values[0].trim();
                String timeInCode = values.length > 1 ? values[1].trim() : "";
                String timeOutCode = values.length > 2 ? values[2].trim() : "";
                String generatedTimeInCode = values.length > 3 ? values[3].trim() : "";
                String generatedTimeOutCode = values.length > 4 ? values[4].trim() : "";

                // Create a new StudentAttendance object and add it to the list
                StudentAttendance record = new StudentAttendance(
                        studentNumber, timeInCode, timeOutCode, generatedTimeInCode, generatedTimeOutCode);
                attendanceRecords.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }

        return attendanceRecords;
    }
}
