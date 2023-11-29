package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication extends JFrame {

    public MainApplication() {
        initUI();
    }

    private void initUI() {
        setTitle("Attendance Tracker");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));

        JButton viewStudentsButton = new JButton("View Student Data");

        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewStudentsScreen();
            }
        });

        mainPanel.add(viewStudentsButton);

        add(mainPanel);
    }

    private void openViewStudentsScreen() {
        ViewStudentsFrame viewStudentsFrame = new ViewStudentsFrame();
        viewStudentsFrame.setVisible(true);
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