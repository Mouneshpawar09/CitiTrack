import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static void connect() {
        try {
            // Change DB URL, username, password as per your setup
            String url = "jdbc:mysql://localhost:3306/citi_track";
            String user = "root";
            String password = "Mounesh@966"; // set your MySQL password

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database disconnected.");
            }
        } catch (SQLException e) {
            System.out.println("Error disconnecting from DB: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
