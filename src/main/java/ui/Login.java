package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.AuthenticationService;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public Login() {
        setUndecorated(true);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        addTitlePanelComponents(titlePanel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("ICONS.png");
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, this);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addMainPanelComponents(mainPanel);
        add(mainPanel, BorderLayout.CENTER);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    private void addTitlePanelComponents(JPanel titlePanel) {
        ImageIcon logoIcon = new ImageIcon("ICONS.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);

        JLabel logoLabel = new JLabel(logoIcon);
        titlePanel.add(logoLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("  ICONS Attendance Tracker");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon closeIcon = new ImageIcon("close_icon.png");
        Image closeImage = closeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        closeIcon = new ImageIcon(closeImage);

        JButton closeButton = new JButton(closeIcon);
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));
        titlePanel.add(closeButton, BorderLayout.EAST);
    }

    private void addMainPanelComponents(JPanel mainPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);

        JLabel userLabel = new JLabel("Username: ");
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(userLabel, gbc);

        gbc.gridy++;
        userField = new JTextField(15);
        mainPanel.add(userField, gbc);

        gbc.gridy++;
        JLabel passLabel = new JLabel("Password: ");
        passLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(passLabel, gbc);

        gbc.gridy++;
        passField = new JPasswordField(15);
        mainPanel.add(passField, gbc);

        gbc.gridy++;
        JButton loginButton = new JButton("Login");
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> authenticateUser());
    }

    private void authenticateUser() {
        String username = userField.getText();
        char[] password = passField.getPassword();

        AuthenticationService authService = new AuthenticationService();
        System.out.println("Authenticating user " + username);

        if (authService.authenticate(username, password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            openMainApplication();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    private void openMainApplication() {
        MainApplication mainApp = new MainApplication();
        mainApp.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        Login login = new Login();
        login.setVisible(true);
    }
}