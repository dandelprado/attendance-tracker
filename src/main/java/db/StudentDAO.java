package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Student;

public class StudentDAO {
    private DatabaseConnect dbConnect = new DatabaseConnect();

    public List<Student> getStudentsByYearLevel(int yearLevel) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = dbConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM Students WHERE YearLevel = ?")) {

            statement.setInt(1, yearLevel);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = mapResultSetToStudent(resultSet);
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students by year level", e);
        }

        return students;
    }

    public List<Student> getStudentsByBirthdayMonth(int month) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = dbConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM Students WHERE MONTH(Birthday) = ?")) {

            statement.setInt(1, month);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = mapResultSetToStudent(resultSet);
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students by birthday month", e);
        }

        return students;
    }

    private Student mapResultSetToStudent(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        String studentNumber = resultSet.getString("StudentNumber");
        String email = resultSet.getString("Email");
        String contactNumber = resultSet.getString("ContactNumber");
        java.sql.Date birthday = resultSet.getDate("Birthday");
        int yearLevel = resultSet.getInt("YearLevel");

        return new Student(firstName, lastName, studentNumber, email, contactNumber, birthday, yearLevel);
    }
}