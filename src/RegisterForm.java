import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterForm extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterForm() {
        setTitle("Register");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setModal(true); // Make it a modal dialog

        // Set up the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Register button
        gbc.gridx = 1;
        gbc.gridy = 2;
        registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        // ActionListener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Call the method to register the user
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(RegisterForm.this, "Registration successful!");
                    dispose(); // Close the dialog
                } else {
                    JOptionPane.showMessageDialog(RegisterForm.this, "Registration failed. Username might be taken.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the panel to the dialog
        add(panel);
        setVisible(true); // Show the registration dialog
    }

    private boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true; // Registration successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Registration failed
        }
    }
}
