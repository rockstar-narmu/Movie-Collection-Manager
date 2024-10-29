import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private String username; // Store the logged-in user's name
    private int userId; // Store the logged-in user's ID

    public Dashboard(String username, int userId) {
        this.username = username;
        this.userId = userId; // Initialize userId

        // Set up the JFrame
        setTitle("Movie Collection Manager - Dashboard");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Welcome message with the user's name
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Create buttons
        JButton addMovieButton = new JButton("Add Movies");
        JButton viewMovieButton = new JButton("View Movies");
        JButton exitButton = new JButton("Exit");

        // Set up the button panel (horizontal alignment)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Use FlowLayout for horizontal alignment
        buttonPanel.add(addMovieButton);
        buttonPanel.add(viewMovieButton);
        buttonPanel.add(exitButton);

        // Add the button panel to the center of the main panel
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add panel to the JFrame
        add(panel);

        // Action Listeners for the buttons
        addMovieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Add Movies panel
                new AddMoviesForm(username, Dashboard.this); // Pass the username to the AddMoviesForm
                setVisible(false);
            }
        });

        viewMovieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the View Movies panel
                new ViewMoviesPanel(userId); // Pass the userId to the ViewMoviesPanel
                //setVisible(false);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // Set the window to visible
        setVisible(true);
    }
}
