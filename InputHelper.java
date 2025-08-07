import java.util.Scanner;

public class InputHelper {
    static Scanner sc = new Scanner(System.in);

    public static int getIntInput() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        return sc.nextInt();
    }

    public static String getLineInput() {
        sc.nextLine(); // consume newline
        return sc.nextLine();
    }

    public static String getNextInput() {
        return sc.next();
    }
}
