import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginForm() {
        // Set up the login form GUI
        setTitle("Movie Collection Manager - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // GridBagConstraints for positioning components
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

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        // Register button
        gbc.gridx = 1;
        registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        // ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the login is successful and get userId
                int userId = DatabaseConnection.checkLogin(username, password);

                if (userId != -1) { // Assuming -1 means login failed
                    // Login successful, open the dashboard
                    new Dashboard(username, userId); // Pass both username and userId to the Dashboard
                    dispose(); // Close the login form
                } else {
                    // Login failed, show an error message
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterForm(); // Open registration form
            }
        });

        add(panel); // Add the panel to the frame
        setVisible(true); // Show the login form
    }

    public static void main(String[] args) {
        new LoginForm(); // Run the login form
    }
}
