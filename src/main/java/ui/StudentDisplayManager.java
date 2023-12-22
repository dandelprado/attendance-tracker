package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import db.DatabaseConnect;

public class StudentDisplayManager {

    public void displayStudentsWithFields(
            boolean includeEmail, boolean includeContactNumber,
            boolean includeBirthday, String yearLevel) {

        int year;
        if (yearLevel.isEmpty() || yearLevel.equalsIgnoreCase("All")) {
            year = -1;
        } else {
            try {
                year = Integer.parseInt(yearLevel);
                if (year < 1 || year > 4) {
                    JOptionPane.showMessageDialog(null, "Year level must be between 1 and 4.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid year level.");
                return;
            }
        }

        List<String> fields = new ArrayList<>();
        fields.add("Name");
        fields.add("StudentNumber");

        if (includeEmail)
            fields.add("Email");
        if (includeContactNumber)
            fields.add("ContactNumber");
        if (includeBirthday)
            fields.add("Birthday");
        if (includeAbsences)
            fields.add("Absences");

        String query = createQuery(fields, yearLevel);

        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            if (year != -1) {
                stmt.setInt(1, year);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                ResultDisplayFrame resultDisplayFrame = new ResultDisplayFrame();
                List<String[]> results = new ArrayList<>();

                results.add(fields.toArray(new String[0]));

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    for (String field : fields) {
                        row.add(rs.getString(field));
                    }
                    results.add(row.toArray(new String[0]));
                }
                resultDisplayFrame.displayResults(results);
                resultDisplayFrame.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while retrieving student information.");
            ex.printStackTrace();
        }
    }

    private String createQuery(List<String> fields, String yearLevel) {
        String fieldNames = fields.stream()
                .map(field -> field.equals("Name") ? "CONCAT(FirstName, ' ', LastName) AS Name" : field)
                .collect(Collectors.joining(", "));

        if (yearLevel.isEmpty() || yearLevel.equalsIgnoreCase("All")) {
            return "SELECT " + fieldNames + " FROM Students WHERE YearLevel >= 1 AND YearLevel <= 4";
        } else {
            return "SELECT " + fieldNames + " FROM Students WHERE YearLevel = ?";
        }
    }
}