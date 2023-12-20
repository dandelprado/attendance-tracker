package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import model.StudentAttendance;

public class StudentDAO {

    private DatabaseConnect dbConnect;

    public StudentDAO() {
        dbConnect = new DatabaseConnect();
    }

    public void updateStudentAbsences(List<StudentAttendance> attendanceRecords) {
        String sql = "UPDATE Students SET Absences = Absences + ? WHERE StudentNumber = ?";

        try (Connection conn = dbConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (StudentAttendance record : attendanceRecords) {
                pstmt.setInt(1, record.getAbsences());
                pstmt.setString(2, record.getStudentNumber());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper error handling
        }
    }
}
