package db;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {
    private DatabaseConnect dbConnect = new DatabaseConnect();

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexHash = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hexHash.append(String.format("%02x", hashByte));
            }

            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean authenticate(String username, char[] password) {
        String hashedPassword = hashPassword(new String(password));

        try (Connection connection = dbConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM Users WHERE username = ? AND password_hash = ?")) {

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    System.out.println("Found user: " + (count > 0)); 
                    return count > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while authenticating: " + e.getMessage()); 
            throw new RuntimeException("Failed to authenticate user.", e);
        }
    }
}