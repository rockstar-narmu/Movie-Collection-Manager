import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMoviesForm extends JFrame {
    private JTextField titleField, genreField, yearField, directorField, castField, ratingField;
    private JComboBox<String> statusBox;
    private JButton addButton, backButton;
    private String loggedInUser;  // Store the logged-in user's name
    private Dashboard dashboard;   // Assuming you have a Dashboard class

    public AddMoviesForm(String loggedInUser, Dashboard dashboard) {
        this.loggedInUser = loggedInUser;
        this.dashboard = dashboard; // Pass the dashboard instance

        setTitle("Add Movie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the panel with GridBagLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Adding components to the panel
        int row = 0;

        // Title
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField = new JTextField(20), gbc);
        row++;

        // Genre
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        panel.add(genreField = new JTextField(20), gbc);
        row++;

        // Release Year
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Release Year:"), gbc);
        gbc.gridx = 1;
        panel.add(yearField = new JTextField(20), gbc);
        row++;

        // Director
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        panel.add(directorField = new JTextField(20), gbc);
        row++;

        // Cast
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Cast:"), gbc);
        gbc.gridx = 1;
        panel.add(castField = new JTextField(20), gbc);
        row++;

        // Rating
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        panel.add(ratingField = new JTextField(20), gbc);
        row++;

        // Status
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusBox = new JComboBox<>(new String[] {"yet to watch", "watched"});
        panel.add(statusBox, gbc);
        row++;

        // Add Movie button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1; // Reset to 1 for separate buttons
        addButton = new JButton("Add Movie");
        panel.add(addButton, gbc);

        // Back button
        gbc.gridx = 1;
        backButton = new JButton("Back");
        panel.add(backButton, gbc);

        // Action Listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovieToDatabase();
            }
        });

        // Action Listener for Back button
        backButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Dispose the current form and show the dashboard
        dispose();
        dashboard.setVisible(true); // Assuming you have a method to show the dashboard
    }
});


        // Add the panel to the frame
        add(panel);
        setVisible(true);
    }

    private void addMovieToDatabase() {
    String title = titleField.getText();
    String genre = genreField.getText();
    int releaseYear = Integer.parseInt(yearField.getText());
    String director = directorField.getText();
    String cast = castField.getText();
    float rating = Float.parseFloat(ratingField.getText());
    String status = (String) statusBox.getSelectedItem();

    if (!title.isEmpty() && !genre.isEmpty()) {
        try {
            DatabaseConnection.addMovie(loggedInUser, title, genre, releaseYear, director, cast, rating, status);
            JOptionPane.showMessageDialog(this, "Movie added successfully!");
            
            // Clear input fields after successful addition
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding movie: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
    }
}


    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        yearField.setText("");
        directorField.setText("");
        castField.setText("");
        ratingField.setText("");
        statusBox.setSelectedIndex(0); // Reset to first item
    }
}
