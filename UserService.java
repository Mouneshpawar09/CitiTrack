import java.sql.*;

public class UserService {
    public static void registerUser() {
        System.out.print("Enter name: ");
        String name = InputHelper.getLineInput();

        System.out.print("Enter email: ");
        String email = InputHelper.getNextInput();

        System.out.print("Enter password: ");
        String password = InputHelper.getNextInput();

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("Registration successful!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void loginUser() {
        System.out.print("Enter email: ");
        String email = InputHelper.getNextInput();

        System.out.print("Enter password: ");
        String password = InputHelper.getNextInput();

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(
                "SELECT user_id, name FROM users WHERE email = ? AND password = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                System.out.println("Welcome, " + name + "!");
                showUserMenu(userId);
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
    }

    private static void showUserMenu(int userId) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. File Complaint");
            System.out.println("2. View My Complaints");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int ch = InputHelper.getIntInput();

            switch (ch) {
                case 1 -> fileComplaint(userId);
                case 2 -> viewComplaints(userId);
                case 3 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void fileComplaint(int userId) {
        System.out.println("Departments:");
        System.out.println("1. Roads\n2. Street Lights\n3. Sanitation\n4. Drainage\n5. Garbage Collection\n6. Street Signs\n7. Potholes");
        System.out.print("Enter department ID (1–7): ");
        int deptId = InputHelper.getIntInput();

        System.out.print("Enter complaint description: ");
        String description = InputHelper.getLineInput();

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(
                "INSERT INTO complaints (user_id, dept_id, description) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, deptId);
            stmt.setString(3, description);
            stmt.executeUpdate();
            System.out.println("Complaint filed successfully!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void viewComplaints(int userId) {
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(
                "SELECT c.complaint_id, d.dept_name, c.description, c.status, c.date_reported " +
                        "FROM complaints c JOIN departments d ON c.dept_id = d.dept_id WHERE c.user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nYour Complaints:");
            while (rs.next()) {
                System.out.printf("ID: %d | Dept: %s | Status: %s | Reported: %s\nDescription: %s\n\n",
                        rs.getInt("complaint_id"),
                        rs.getString("dept_name"),
                        rs.getString("status"),
                        rs.getTimestamp("date_reported"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
