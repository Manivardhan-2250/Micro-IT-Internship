import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class PasswordGenerator {
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    public static String generatePassword(int length, boolean useUppercase, boolean useLowercase, 
                                        boolean useNumbers, boolean useSpecial) {
        StringBuilder charPool = new StringBuilder();
        ArrayList<Character> password = new ArrayList<>();
        Random random = new Random();

        // Build character pool based on user preferences
        if (useUppercase) charPool.append(UPPERCASE_CHARS);
        if (useLowercase) charPool.append(LOWERCASE_CHARS);
        if (useNumbers) charPool.append(NUMBER_CHARS);
        if (useSpecial) charPool.append(SPECIAL_CHARS);

        // Validate character pool
        if (charPool.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be selected!");
        }

        // Ensure at least one character from each selected type
        if (useUppercase) {
            password.add(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));
        }
        if (useLowercase) {
            password.add(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));
        }
        if (useNumbers) {
            password.add(NUMBER_CHARS.charAt(random.nextInt(NUMBER_CHARS.length())));
        }
        if (useSpecial) {
            password.add(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));
        }

        // Check if length is sufficient
        if (length < password.size()) {
            throw new IllegalArgumentException("Password length too short for selected character types!");
        }

        // Fill remaining length with random characters
        for (int i = password.size(); i < length; i++) {
            password.add(charPool.charAt(random.nextInt(charPool.length())));
        }

        // Shuffle password
        Collections.shuffle(password, random);

        // Convert to string
        StringBuilder result = new StringBuilder();
        for (Character c : password) {
            result.append(c);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Password Generator");
            System.out.print("Enter password length (minimum 4): ");
            int length = Integer.parseInt(scanner.nextLine());

            System.out.print("Include uppercase letters? (y/n): ");
            boolean useUppercase = scanner.nextLine().trim().toLowerCase().equals("y");

            System.out.print("Include lowercase letters? (y/n): ");
            boolean useLowercase = scanner.nextLine().trim().toLowerCase().equals("y");

            System.out.print("Include numbers? (y/n): ");
            boolean useNumbers = scanner.nextLine().trim().toLowerCase().equals("y");

            System.out.print("Include special characters? (y/n): ");
            boolean useSpecial = scanner.nextLine().trim().toLowerCase().equals("y");

            String password = generatePassword(length, useUppercase, useLowercase, useNumbers, useSpecial);
            System.out.println("\nGenerated Password: " + password);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format for length!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}