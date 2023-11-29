package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateCodeFrame extends JFrame {

    private Set<String> inCodes;
    private Set<String> outCodes;
    private JTextArea codesTextArea;
    private JTextField eventNameField;
    private JTextField eventDateField;
    private JButton saveButton;

    public GenerateCodeFrame() {
        inCodes = new HashSet<>();
        outCodes = new HashSet<>();
        codesTextArea = new JTextArea();
        eventNameField = new JTextField();
        eventDateField = new JTextField();
        initUI();
    }

    private void initUI() {
        setTitle("Generate Codes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JButton generateButton = new JButton("Generate Codes");
        saveButton = new JButton("Save to PDF");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCodes();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToPDF();
            }
        });

        mainPanel.add(new JScrollPane(codesTextArea), BorderLayout.CENTER);
        mainPanel.add(generateButton, BorderLayout.NORTH);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private int getNumberOfPairs() {
        String input = JOptionPane.showInputDialog("Enter the number of code pairs to generate:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getEventName() {
        return JOptionPane.showInputDialog(this, "Enter Event Name:");
    }

    private String getEventDate() {
        return JOptionPane.showInputDialog(this, "Enter Event Date (MM/dd):");
    }

    private void generateCodes() {
        int numberOfPairs = getNumberOfPairs();

        if (numberOfPairs <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of pairs.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] options = { "Half Day", "Full Day" };
        int response = JOptionPane.showOptionDialog(null, "Is the event half day or full day?", "Event Duration",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (response == 1) {
            numberOfPairs *= 2;
        }

        String eventName = getEventName();
        String eventDate = getEventDate();

        if (eventName.isEmpty() || eventDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the event name and date.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String documentName = eventName + " - " + eventDate + ".pdf";

        inCodes.clear();
        outCodes.clear();
        codesTextArea.setText("");

        for (int i = 0; i < numberOfPairs; i++) {
            String inCode = generateRandomCode();
            String outCode = generateRandomCode();

            inCodes.add(inCode);
            outCodes.add(outCode);

            codesTextArea.append("IN Code: " + inCode + "\t\t");
            codesTextArea.append("OUT Code: " + outCode + "\n");
        }

        eventNameField.setText(eventName);
        eventDateField.setText(eventDate);
        eventNameField.setEnabled(true);
        eventDateField.setEnabled(true);

        saveButton.setEnabled(true);
        saveButton.setActionCommand(documentName);
    }

    private void saveToPDF() {
        String eventName = eventNameField.getText();
        String eventDate = eventDateField.getText();

        if (eventName.isEmpty() || eventDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Event Name and Event Date.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd");
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(eventDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter date in MM/dd format.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = eventName + " - " + eventDate.replace("/", "-") + ".pdf";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(fileChooser.getCurrentDirectory(), fileName));
        fileChooser.setDialogTitle("Save PDF");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();

                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                boolean isFullDay = inCodes.size() > getNumberOfPairs();

                document.add(new Paragraph(eventName + " - " + eventDate));
                document.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(2);
                table.addCell("Morning IN Code");
                table.addCell("Morning OUT Code");

                int count = 0;
                for (String inCode : inCodes) {
                    if (isFullDay && count == inCodes.size() / 2) {
                        document.add(table);
                        table = new PdfPTable(2);
                        table.addCell("Afternoon IN Code");
                        table.addCell("Afternoon OUT Code");
                    }
                    table.addCell(inCode);
                    table.addCell(outCodes.iterator().next());
                    outCodes.remove(outCodes.iterator().next());
                    count++;
                }

                document.add(table);

                document.close();

                JOptionPane.showMessageDialog(null, "PDF saved successfully: " + filePath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error saving PDF: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
}
