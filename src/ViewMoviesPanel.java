import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMoviesPanel extends JFrame {
    private int userId; // Store the logged-in user's ID
    private JTable moviesTable; // Table to display movies
    private Object[][] data; // Store fetched movie data

    public ViewMoviesPanel(int userId) {
        this.userId = userId; // Set the user ID

        // Set up the JFrame
        setTitle("View Movies");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a panel for the table
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a table model to hold movie data, including ID
        String[] columnNames = {"ID", "Title", "Genre", "Release Year", "Director", "Cast", "Rating", "Status"};
        data = fetchMoviesFromDatabase(); // Fetch movies from the database

        // Create the table
        moviesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(moviesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit Movie");
        JButton deleteButton = new JButton("Delete Movie");
        JButton backButton = new JButton("Back");

        // Add action listeners
        editButton.addActionListener(e -> editMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        backButton.addActionListener(e -> dispose()); // Close the ViewMoviesPanel

        // Add buttons to the button panel
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);
        setVisible(true);
    }

    private Object[][] fetchMoviesFromDatabase() {
        Object[][] data = new Object[0][]; // Initialize empty data array
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, title, genre, release_year, director, cast, rating, status FROM movies WHERE user_id = ?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            pstmt.setInt(1, userId); // Set the user ID parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                // Count the number of rows
                rs.last(); // Move to the last row
                int rowCount = rs.getRow(); // Get the row count
                rs.beforeFirst(); // Move back to the start

                // Initialize the data array
                data = new Object[rowCount][8]; // Update to 8 columns for ID
                int rowIndex = 0;

                // Fill the data array with results
                while (rs.next()) {
                    data[rowIndex][0] = rs.getInt("id"); // Add movie ID
                    data[rowIndex][1] = rs.getString("title");
                    data[rowIndex][2] = rs.getString("genre");
                    data[rowIndex][3] = rs.getInt("release_year");
                    data[rowIndex][4] = rs.getString("director");
                    data[rowIndex][5] = rs.getString("cast");
                    data[rowIndex][6] = rs.getFloat("rating");
                    data[rowIndex][7] = rs.getString("status");
                    rowIndex++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching movies: " + e.getMessage());
        }
        return data;
    }

    private void deleteMovie() {
        int selectedRow = moviesTable.getSelectedRow();
        if (selectedRow != -1) {
            int movieId = (Integer) moviesTable.getValueAt(selectedRow, 0); // Get movie ID
            String title = (String) moviesTable.getValueAt(selectedRow, 1);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete \"" + title + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseConnection.connect();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM movies WHERE id = ? AND user_id = ?")) {
                    pstmt.setInt(1, movieId); // Use movie ID for deletion
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Movie deleted successfully.");
                    refreshMovies(); // Refresh the movie list
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting movie: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.");
        }
    }
    
    private void editMovie() {
    int selectedRow = moviesTable.getSelectedRow();
    if (selectedRow != -1) {
        int movieId = (Integer) moviesTable.getValueAt(selectedRow, 0); // Get movie ID
        String title = (String) moviesTable.getValueAt(selectedRow, 1);
        String genre = (String) moviesTable.getValueAt(selectedRow, 2);
        int releaseYear = (Integer) moviesTable.getValueAt(selectedRow, 3);
        String director = (String) moviesTable.getValueAt(selectedRow, 4);
        String cast = (String) moviesTable.getValueAt(selectedRow, 5);
        float rating = (Float) moviesTable.getValueAt(selectedRow, 6);
        String status = (String) moviesTable.getValueAt(selectedRow, 7);

        // Open the Edit Movie dialog
        new EditMovieDialog(this, userId, movieId, title, genre, releaseYear, director, cast, rating, status);
        refreshMovies(); // Refresh the movie list to show updates
    } else {
        JOptionPane.showMessageDialog(this, "Please select a movie to edit.");
    }
}


    private void refreshMovies() {
        data = fetchMoviesFromDatabase();
        moviesTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"ID", "Title", "Genre", "Release Year", "Director", "Cast", "Rating", "Status"}));
    }
}
