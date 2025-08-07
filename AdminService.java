import java.sql.*;

public class AdminService {
    private static final String ADMIN_EMAIL = "admin@citi.com";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void loginAdmin() {
        System.out.print("Enter admin email: ");
        String email = InputHelper.getNextInput();

        System.out.print("Enter password: ");
        String password = InputHelper.getNextInput();

        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Welcome, Admin!");
            showAdminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Complaints");
            System.out.println("2. Update Complaint Status");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int ch = InputHelper.getIntInput();

            switch (ch) {
                case 1 -> viewAllComplaints();
                case 2 -> updateStatus();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewAllComplaints() {
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT c.complaint_id, u.name, d.dept_name, c.description, c.status, c.date_reported " +
                             "FROM complaints c JOIN users u ON c.user_id = u.user_id " +
                             "JOIN departments d ON c.dept_id = d.dept_id")) {

            System.out.println("\nAll Complaints:");
            while (rs.next()) {
                System.out.printf("ID: %d | User: %s | Dept: %s | Status: %s | Date: %s\nDescription: %s\n\n",
                        rs.getInt("complaint_id"),
                        rs.getString("name"),
                        rs.getString("dept_name"),
                        rs.getString("status"),
                        rs.getTimestamp("date_reported"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void updateStatus() {
        System.out.print("Enter complaint ID to update: ");
        int complaintId = InputHelper.getIntInput();

        System.out.print("Enter new status (Pending/In Progress/Resolved): ");
        String status = InputHelper.getLineInput();

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(
                "UPDATE complaints SET status = ? WHERE complaint_id = ?")) {
            stmt.setString(1, status);
            stmt.setInt(2, complaintId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Status updated successfully.");
            } else {
                System.out.println("Complaint ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating status: " + e.getMessage());
        }
    }
}
