
public class CitiTrackMainApp {
    public static void main(String[] args) {
        Database.connect();  // Initialize DB connection

        while (true) {
            System.out.println("\nWelcome to CitiTrack Complaint Tracker");
            System.out.println("1. Register as User");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = InputHelper.getIntInput();

            switch (choice) {
                case 1 -> UserService.registerUser();
                case 2 -> UserService.loginUser();
                case 3 -> AdminService.loginAdmin();
                case 4 -> {
                    Database.disconnect();
                    System.out.println("Thank you for using CitiTrack.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
