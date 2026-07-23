package main.java.com.airtribe.learntrack;

import main.java.com.airtribe.learntrack.service.StudentService;
import main.java.com.airtribe.learntrack.utils.InputValidator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        printBanner();
        System.out.println();
        System.out.println("Application started successfully!");

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            displayMainMenu();
            choice = getUserChoice(scanner);
            // Pass the scanner into the execution router to handle sub-menus
            executeMainAction(choice, scanner);
        } while (choice != 4);

        scanner.close();
        System.out.println("Program terminated safely.");
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=================================");
        System.out.println("        MAIN CONSOLE MENU        ");
        System.out.println("=================================");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Exit Application");
        System.out.print("Please enter your choice (1-4): ");
    }

    private static void executeMainAction(int choice, Scanner scanner) {
        System.out.println();
        switch (choice) {
            case 1:
                handleStudentManagement(scanner);
                break;
            case 2:
                System.out.println("Action executed: Fetching data and generating Reports...");
                break;
            case 3:
                System.out.println("Action executed: All systems are fully operational.");
                break;
            case 4:
                System.out.println("Thank you for using the system. Goodbye!");
                break;
            default:
                System.out.println("Error: Invalid entry. Please enter a number between 1 and 4.");
                break;
        }
    }

    private static void handleStudentManagement(Scanner scanner) {
        int subChoice = 0;

        // Sub-menu loop continues until user inputs the 'Go Back' option (3)
        do {
            System.out.println("\n--- STUDENT MANAGEMENT SUB-MENU ---");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Please enter your sub-choice (1-4): ");

            subChoice = getUserChoice(scanner);
            System.out.println();

            switch (subChoice) {
                case 1:
                    scanner.nextLine();
                    createStudentProfile(scanner);
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    getStudentById(scanner);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Error: Invalid sub-entry. Enter a number between 1 and 4.");
                    break;
            }
        } while (subChoice != 4);
    }

    private static void createStudentProfile(Scanner scanner) {
        System.out.println("--- ENTER STUDENT DETAILS ---");

        // 1. Capture First Name
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();

        // 2. Capture Last Name
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();

        // 3. Capture Email with a basic validation check
        String email = "";
        while (true) {
            System.out.print("Enter Email Address: ");
            email = scanner.nextLine().trim();

            if (InputValidator.isValidEmail(email)) {
                break; // Valid layout, exit validation loop
            }
            System.out.println("Invalid email format. Try again (e.g., name@domain.com).");
        }
        studentService.addStudent(firstName, lastName, email);
    }

    private static void listStudents() {
        studentService.listStudents();
    }

    private static void getStudentById(Scanner scanner) {
        System.out.println("Enter student id to search for: ");
        int studentId = scanner.nextInt();
        studentService.searchStudentById(studentId);
    }

    private static void printBanner() {
        // Reads the file from the classpath
        try (InputStream is = Main.class.getResourceAsStream("/banner.txt")) {
            if (is == null) {
                System.out.println("Banner file not found.");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load banner: " + e.getMessage());
        }
    }

}
