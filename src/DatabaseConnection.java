import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MovieDB";
    private static final String DB_USER = "narmadha"; // Your MySQL username
    private static final String DB_PASS = "cat&bunny"; // Your MySQL password

    public static Connection connect() {
    Connection conn = null;
    try {
        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish the connection
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        System.out.println("Connected to the database successfully!");
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Connection failed: " + e.getMessage());
    }
    return conn; // Return null if connection failed
}

public static int checkLogin(String username, String password) {
    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ?")) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("user_id"); // Return the user ID
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Invalid username or password
}

public static String getUserIdFromUsername(String username) throws SQLException {
    String userId = null;
    String query = "SELECT user_id FROM users WHERE username = ?";

    try (Connection connection = connect();  // Use the connect method
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
         
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            userId = resultSet.getString("user_id");
        }
    }
    return userId;
}

public static void addMovie(String loggedInUser, String title, String genre, int releaseYear, String director, String cast, float rating, String status) {
    // Retrieve user_id from loggedInUser
    String userId;
    try {
        userId = getUserIdFromUsername(loggedInUser);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to retrieve user ID: " + e.getMessage());
    }
    
    if (userId == null) {
        throw new IllegalArgumentException("User does not exist");
    }

    // Insert movie into the database
    String query = "INSERT INTO movies (user_id, title, genre, release_year, director, cast, rating, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = connect();
         PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, userId);
        pstmt.setString(2, title);
        pstmt.setString(3, genre);
        pstmt.setInt(4, releaseYear);
        pstmt.setString(5, director);
        pstmt.setString(6, cast);
        pstmt.setFloat(7, rating);
        pstmt.setString(8, status);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
