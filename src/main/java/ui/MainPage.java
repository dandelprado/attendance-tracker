package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    private JButton loginButton;
    private JButton signUpButton;

    public MainPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Welcome to Attendance Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginForm();
            }
        });
        add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignUpForm();
            }
        });
        add(signUpButton);

        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void openLoginForm() {
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }

    private void openSignUpForm() {
        Registration registrationForm = new Registration();
        registrationForm.setVisible(true);
    }

    public static void main(String[] args) {
        new MainPage();
    }
}