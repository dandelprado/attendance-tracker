package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import db.DatabaseConnect;
import db.StudentDAO;
import model.AbsenceCalculator;
import model.StudentAttendance;
import utility.CSVParser;

public class MainApplication extends JFrame {

    public MainApplication() {
        initUI();
    }

    private void initUI() {
        setTitle("Attendance Tracker");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JButton viewStudentsButton = new JButton("View Student Data");
        JButton uploadCSVButton = new JButton("Upload CSV File");

        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewStudentsScreen();
            }
        });

        uploadCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCSVFileChooser();
            }
        });

        JButton generateReportButton = new JButton("Generate Absence Report");
        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainApplication.this.generateAttendancePDF();

            }
        });
        mainPanel.add(generateReportButton);
        mainPanel.add(viewStudentsButton);
        mainPanel.add(uploadCSVButton);

        add(mainPanel);
    }

    public void generateAttendancePDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a directory to save your report:");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (PDDocument document = new PDDocument()) {
                List<StudentRecord> studentRecords = fetchStudentData();
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Table Header
                String[] headers = { "Name", "Student Number", "# of Absences" };
                float margin = 50;
                float yStart = 700;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20;
                float cellMargin = 5;
                float nexty = yStart;

                writeHeaders(contentStream, headers, margin, cellMargin, nexty, tableWidth);
                nexty -= rowHeight;

                for (StudentRecord record : studentRecords) {
                    if (nexty <= margin + rowHeight) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        nexty = yStart;
                        writeHeaders(contentStream, headers, margin, cellMargin, nexty, tableWidth);
                        nexty -= rowHeight;
                    }
                    writeStudentData(contentStream, record, margin, cellMargin, nexty, tableWidth, headers.length);
                    nexty -= rowHeight;
                }

                contentStream.close();
                File selectedDirectory = fileChooser.getSelectedFile();
                String savePath = selectedDirectory.getAbsolutePath() + File.separator + "AttendanceReport.pdf";
                document.save(savePath);
                JOptionPane.showMessageDialog(null, "Attendance Report saved successfully at " + savePath);
            } catch (IOException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error while generating PDF: " + e.getMessage());
            }
        }
    }

    private void writeHeaders(PDPageContentStream contentStream, String[] headers, float margin, float cellMargin,
            float yStart, float tableWidth) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(margin + cellMargin, yStart - 15);
        for (String header : headers) {
            contentStream.showText(header);
            contentStream.newLineAtOffset(tableWidth / headers.length, 0);
        }
        contentStream.endText();
    }

    private void writeStudentData(PDPageContentStream contentStream, StudentRecord record, float margin,
            float cellMargin, float nexty, float tableWidth, int numColumns) throws IOException {
        String fullName = record.getLastName() + ", " + record.getFirstName();
        contentStream.beginText();
        contentStream.newLineAtOffset(margin + cellMargin, nexty - 15);
        contentStream.showText(fullName);
        contentStream.newLineAtOffset(tableWidth / numColumns, 0);
        contentStream.showText(record.getStudentNumber());
        contentStream.newLineAtOffset(tableWidth / numColumns, 0);
        contentStream.showText(String.valueOf(record.getAbsences()));
        contentStream.endText();
    }

    private List<StudentRecord> fetchStudentData() throws SQLException {
        List<StudentRecord> studentRecords = new ArrayList<>();
        String query = "SELECT LastName, FirstName, StudentNumber, Absences FROM Students";
        try (Connection conn = DatabaseConnect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StudentRecord record = new StudentRecord(
                        rs.getString("LastName"),
                        rs.getString("FirstName"),
                        rs.getString("StudentNumber"),
                        rs.getInt("Absences"));
                studentRecords.add(record);
            }
        }
        return studentRecords;
    }

    class StudentRecord {
        private String lastName;
        private String firstName;
        private String studentNumber;
        private int absences;

        public StudentRecord(String lastName, String firstName, String studentNumber, int absences) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.studentNumber = studentNumber;
            this.absences = absences;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public int getAbsences() {
            return absences;
        }
    }

    private void openViewStudentsScreen() {
        ViewStudentsFrame viewStudentsFrame = new ViewStudentsFrame();
        viewStudentsFrame.setVisible(true);
    }

    private void openCSVFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(MainApplication.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            processCSVFile(filePath);
        }
    }

    private void processCSVFile(String filePath) {
        CSVParser parser = new CSVParser();
        CSVParser.ParseResult parseResult = parser.parseCSV(filePath);
        List<StudentAttendance> attendanceRecords = parseResult.getAttendanceRecords();
        Set<String> generatedTimeInCodes = parseResult.getGeneratedTimeInCodes();
        Set<String> generatedTimeOutCodes = parseResult.getGeneratedTimeOutCodes();

        AbsenceCalculator calculator = new AbsenceCalculator();
        calculator.calculateAbsences(attendanceRecords, generatedTimeInCodes, generatedTimeOutCodes);

        // Update Database
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.updateStudentAbsences(attendanceRecords);

        JOptionPane.showMessageDialog(null, "Attendance data processed and updated successfully.");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        MainApplication mainApp = new MainApplication();
        mainApp.setVisible(true);
    }
}