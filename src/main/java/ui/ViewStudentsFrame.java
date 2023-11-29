package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewStudentsFrame extends JFrame {

    private JCheckBox emailCheckbox;
    private JCheckBox contactNumberCheckbox;
    private JCheckBox birthdayCheckbox;
    private JTextField yearLevelField;

    public ViewStudentsFrame() {
        setTitle("View Students");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel checkBoxPanel = new JPanel(new GridLayout(3, 1));

        yearLevelField = new JTextField(15);
        JLabel yearLevelLabel = new JLabel("Year Level:");

        JPanel yearLevelPanel = new JPanel();
        yearLevelPanel.add(yearLevelLabel);
        yearLevelPanel.add(yearLevelField);

        mainPanel.add(yearLevelPanel, BorderLayout.NORTH);

        emailCheckbox = createCheckBox("Email");
        contactNumberCheckbox = createCheckBox("Contact Number");
        birthdayCheckbox = createCheckBox("Birthday");

        checkBoxPanel.add(emailCheckbox);
        checkBoxPanel.add(contactNumberCheckbox);
        checkBoxPanel.add(birthdayCheckbox);

        JButton displayButton = createDisplayButton();

        mainPanel.add(checkBoxPanel, BorderLayout.CENTER);
        mainPanel.add(displayButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JCheckBox createCheckBox(String text) {
        return new JCheckBox(text);
    }

    private JButton createDisplayButton() {
        JButton button = new JButton("Display Students");
        button.addActionListener(e -> displayStudents());
        return button;
    }

    private void displayStudents() {
        boolean includeEmail = emailCheckbox.isSelected();
        boolean includeContactNumber = contactNumberCheckbox.isSelected();
        boolean includeBirthday = birthdayCheckbox.isSelected();
        String yearLevel = yearLevelField.getText();

        StudentDisplayManager displayManager = new StudentDisplayManager();
        displayManager.displayStudentsWithFields(
                includeEmail, includeContactNumber, includeBirthday, yearLevel);
    }
}