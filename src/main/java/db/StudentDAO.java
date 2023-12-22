package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getAbsenceCounts() {
        Map<String, Integer> absenceCounts = new HashMap<>();
        String sql = "SELECT student_id, COUNT(*) as absence_count FROM absences GROUP BY student_id";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                int count = rs.getInt("absence_count");
                absenceCounts.put(studentId, count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return absenceCounts;
    }

}