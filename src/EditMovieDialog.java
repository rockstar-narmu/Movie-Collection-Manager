import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditMovieDialog extends JDialog {
    private JTextField titleField;
    private JTextField genreField;
    private JTextField releaseYearField;
    private JTextField directorField;
    private JTextField castField;
    private JTextField ratingField;
    private JComboBox<String> statusComboBox; // Dropdown for status
    private int movieId; // Movie ID for the movie being edited

    public EditMovieDialog(JFrame parent, int user, int movieId, String title, String genre, int releaseYear, String director, String cast, float rating, String status) {
        super(parent, "Edit Movie", true);
        this.movieId = movieId; // Store movie ID

        // Set up the dialog
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Create input fields with existing data
        titleField = new JTextField(20);
        titleField.setText(title); // Set existing title

        genreField = new JTextField(20);
        genreField.setText(genre); // Set existing genre

        releaseYearField = new JTextField(20);
        releaseYearField.setText(String.valueOf(releaseYear)); // Set existing release year

        directorField = new JTextField(20);
        directorField.setText(director); // Set existing director

        castField = new JTextField(20);
        castField.setText(cast); // Set existing cast

        ratingField = new JTextField(20);
        ratingField.setText(String.valueOf(rating)); // Set existing rating

        // Create a dropdown for status with the existing status selected
        String[] statuses = {"Yet to Watch", "Watched"};
        statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(status); // Set the current status

        // Adding components to the dialog
        int row = 0;

        // Title
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        add(titleField, gbc);
        row++;

        // Genre
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        add(genreField, gbc);
        row++;

        // Release Year
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Release Year:"), gbc);
        gbc.gridx = 1;
        add(releaseYearField, gbc);
        row++;

        // Director
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        add(directorField, gbc);
        row++;

        // Cast
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Cast:"), gbc);
        gbc.gridx = 1;
        add(castField, gbc);
        row++;

        // Rating
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        add(ratingField, gbc);
        row++;

        // Status
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        add(statusComboBox, gbc);
        row++;

        // Save button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1; // Reset to 1 for separate buttons
        JButton saveButton = new JButton("Save");
        add(saveButton, gbc);

        // Cancel button
        gbc.gridx = 1;
        JButton cancelButton = new JButton("Cancel");
        add(cancelButton, gbc);

        // Action listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                dispose(); // Close the dialog
            }
        });

        cancelButton.addActionListener(e -> dispose()); // Close the dialog without saving

        setVisible(true);
    }

    private void saveChanges() {
        String title = titleField.getText();
        String genre = genreField.getText();
        int releaseYear = Integer.parseInt(releaseYearField.getText());
        String director = directorField.getText();
        String cast = castField.getText();
        float rating = Float.parseFloat(ratingField.getText());
        String status = (String) statusComboBox.getSelectedItem(); // Get selected status

        // Update the movie in the database
        String query = "UPDATE movies SET title = ?, genre = ?, release_year = ?, director = ?, cast = ?, rating = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setInt(3, releaseYear);
            pstmt.setString(4, director);
            pstmt.setString(5, cast);
            pstmt.setFloat(6, rating);
            pstmt.setString(7, status);
            pstmt.setInt(8, movieId); // Use movie ID for the update
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Movie updated successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating movie: " + e.getMessage());
        }
    }
}
