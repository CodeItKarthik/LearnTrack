package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exceptions.*;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.utils.InputValidator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();

    public static void main(String[] args) {
        printBanner();
        System.out.println();
        System.out.println("Application started successfully!");

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        try {
            do {
                displayMainMenu();
                choice = getUserChoice(scanner);
                // Pass the scanner into the execution router to handle sub-menus
                executeMainAction(choice, scanner);
            } while (choice != 4);
        } catch (SystemExitException e) {
            System.out.println(e);
        } finally {
            scanner.close();
            System.out.println("Program terminated safely.");
        }
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
        System.out.println(MenuOptions.STUDENT_MANAGEMENT_MENU);
        System.out.println(MenuOptions.COURSE_MANAGEMENT_MENU);
        System.out.println(MenuOptions.ENROLLMENT_MANAGEMENT_MENU);
        System.out.println(MenuOptions.EXIT_APPLICATION);
        System.out.print("Please enter your choice (1-4): ");
    }

    private static void executeMainAction(int choice, Scanner scanner) {
        System.out.println();
        switch (choice) {
            case 1:
                handleStudentManagementSubMenu(scanner);
                break;
            case 2:
                handleCourseManagementSubMenu(scanner);
                break;
            case 3:
                handleEnrollmentManagementSubMenu(scanner);
                break;
            case 4:
                System.out.println("Thank you for using the system. Goodbye!");
                break;
            default:
                System.out.println("Error: Invalid entry. Please enter a number between 1 and 4.");
                break;
        }
    }

    private static void handleStudentManagementSubMenu(Scanner scanner) {
        int subChoice = 0;

        // Sub-menu loop continues until user inputs the 'Go Back' option (3)
        do {
            System.out.println("\n--- STUDENT MANAGEMENT SUB-MENU ---");
            System.out.println(MenuOptions.ADD_NEW_STUDENT);
            System.out.println(MenuOptions.VIEW_ALL_STUDENTS);
            System.out.println(MenuOptions.SEARCH_STUDENT_BY_ID);
            System.out.println(MenuOptions.REMOVE_STUDENT_BY_ID);
            System.out.println(MenuOptions.DEACTIVATE_STUDENT_BY_ID);
            System.out.println(MenuOptions.ACTIVATE_STUDENT_BY_ID);
            System.out.println("7. " + MenuOptions.GO_BACK_TO_MAIN_MENU);
            System.out.print("Please enter your sub-choice (1-7): ");

            subChoice = getUserChoice(scanner);
            System.out.println();

            switch (subChoice) {
                case 1:
                    scanner.nextLine();
                    try {
                        createStudentProfile(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    scanner.nextLine();
                    try {
                        getStudentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    try {
                        removeStudentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    try {
                        deactivateStudentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    try {
                        activateStudentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Error: Invalid sub-entry. Enter a number between 1 and 7.");
                    break;
            }
        } while (subChoice != 7);
    }

    private static void createStudentProfile(Scanner scanner) {
        System.out.println("--------------------- ENTER STUDENT DETAILS -------------------------");
        System.out.println("(Type 'cancel' to return to menu, or 'exit' to quit the app entirely)");
        System.out.println("---------------------------------------------------------------------");

        String firstName = null;
        do {
            System.out.print("Enter First Name: ");
            firstName = readSafeInput(scanner);
        } while (!InputValidator.isValidFirstName(firstName));

        String lastName = null;
        do {
            System.out.print("Enter Last Name: ");
            lastName = readSafeInput(scanner);
        } while (!InputValidator.isNotBlank(lastName));

            // 3. Capture Email with a basic validation check
        String email = null;
        while (true) {
            System.out.print("Enter Email Address: ");
            email = readSafeInput(scanner);

            if (InputValidator.isValidEmail(email)) {
                break; // Valid layout, exit validation loop
            }
            System.out.println("============================ Invalid email format. Try again (e.g., name@domain.com) ============================");
        }
        studentService.addStudent(firstName, lastName, email);
    }

    private static void listStudents() {
        try {
            studentService.listStudents();
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void getStudentById(Scanner scanner) {
        try {
            System.out.println("Enter student id to search for: ");
            int studentId = Integer.parseInt(readSafeInput(scanner));
            Student studentSearched = studentService.searchStudentById(studentId);

            if (studentSearched == null) {
                throw new EntityNotFoundException("============================ Student with id " + studentId + " not found ============================");
            } else {
                System.out.println("====================== Details of Student with id: " + studentId + "======================");
                System.out.println(studentSearched);
            }
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EntityNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void removeStudentById(Scanner scanner) {
        try {
            System.out.println("Enter student id to remove: ");
            int studentId = Integer.parseInt(readSafeInput(scanner));
            studentService.removeStudent(studentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void deactivateStudentById(Scanner scanner) {
        try {
            System.out.println("Enter student id to deactivate: ");
            int studentId = Integer.parseInt(readSafeInput(scanner));
            studentService.deactivateStudent(studentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void activateStudentById(Scanner scanner) {
        try {
            System.out.println("Enter student id to activate: ");
            int studentId = Integer.parseInt(readSafeInput(scanner));
            studentService.activateStudent(studentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void handleCourseManagementSubMenu(Scanner scanner) {
        int subChoice = 0;

        // Sub-menu loop continues until user inputs the 'Go Back' option (3)
        do {
            System.out.println("\n--- COURSE MANAGEMENT SUB-MENU ---");
            System.out.println(MenuOptions.ADD_NEW_COURSE);
            System.out.println(MenuOptions.VIEW_ALL_COURSES);
            System.out.println(MenuOptions.SEARCH_COURSE_BY_ID);
            System.out.println(MenuOptions.REMOVE_COURSE_BY_ID);
            System.out.println(MenuOptions.REMOVE_COURSE_BY_NAME);
            System.out.println(MenuOptions.DEACTIVATE_COURSE_BY_ID);
            System.out.println(MenuOptions.ACTIVATE_COURSE_BY_ID);
            System.out.println("8. " + MenuOptions.GO_BACK_TO_MAIN_MENU);
            System.out.print("Please enter your sub-choice (1-8): ");

            subChoice = getUserChoice(scanner);
            System.out.println();

            switch (subChoice) {
                case 1:
                    scanner.nextLine();
                    try {
                        createCourse(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    listCourses();
                    break;
                case 3:
                    scanner.nextLine();
                    try {
                        getCourseById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    try {
                        removeCourseById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    try {
                        removeCourseByName(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    try {
                        deactivateCourseById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 7:
                    scanner.nextLine();
                    try {
                        activateCourseById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 8:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Error: Invalid sub-entry. Enter a number between 1 and 7.");
                    break;
            }
        } while (subChoice != 8);
    }

    private static void createCourse(Scanner scanner) {
        try {
            System.out.println("---------------------- ENTER COURSE DETAILS -------------------------");
            System.out.println("(Type 'cancel' to return to menu, or 'exit' to quit the app entirely)");
            System.out.println("---------------------------------------------------------------------");

            String courseName = null;
            do {
                System.out.print("Enter Course Name: ");
                courseName = readSafeInput(scanner);
            } while (!InputValidator.isValidCourseName(courseName));

            int duration = 0;
            do {
                System.out.print("Enter Course Duration in Weeks: ");
                duration = Integer.parseInt(readSafeInput(scanner));
            } while (!InputValidator.isValidDuration(duration));

            String courseDesc = null;
            do {
                System.out.print("Enter Course Description: ");
                courseDesc = readSafeInput(scanner);
            } while (!InputValidator.descriptionLengthCheck(courseDesc));

            courseService.addCourse(courseName, courseDesc, duration);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        }
    }

    private static void listCourses() {
        try {
            courseService.listCourses();
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void getCourseById(Scanner scanner) {
        try {
            System.out.println("Enter course id to search for: ");
            int courseId = Integer.parseInt(readSafeInput(scanner));
            Course courseSearched = courseService.searchCourseById(courseId);

            if (courseSearched == null) {
                throw new EntityNotFoundException("============================ Course with id " + courseId + " not found ============================");
            } else {
                System.out.println("====================== Details of Course with id: " + courseId + "======================");
                System.out.println(courseSearched);
            }
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EntityNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void removeCourseById(Scanner scanner) {
        try {
            System.out.println("Enter course id to remove: ");
            int courseId = Integer.parseInt(readSafeInput(scanner));
            courseService.removeCourse(courseId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void removeCourseByName(Scanner scanner) {
        try {
            System.out.println("Enter course name to remove: ");
            String courseName = readSafeInput(scanner);
            courseService.removeCourse(courseName);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void deactivateCourseById(Scanner scanner) {
        try {
            System.out.println("Enter course id to deactivate: ");
            int courseId = Integer.parseInt(readSafeInput(scanner));
            courseService.deactivateCourse(courseId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void activateCourseById(Scanner scanner) {
        try {
            System.out.println("Enter course id to activate: ");
            int courseId = Integer.parseInt(readSafeInput(scanner));
            courseService.activateCourse(courseId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void handleEnrollmentManagementSubMenu(Scanner scanner) {
        int subChoice = 0;

        // Sub-menu loop continues until user inputs the 'Go Back' option (3)
        do {
            System.out.println("\n--- ENROLLMENT MANAGEMENT SUB-MENU ---");
            System.out.println(MenuOptions.ADD_NEW_ENROLLMENT);
            System.out.println(MenuOptions.VIEW_ALL_ENROLLMENTS);
            System.out.println(MenuOptions.SEARCH_ENROLLMENT_BY_ID);
            System.out.println(MenuOptions.REMOVE_ENROLLMENT_BY_ID);
            System.out.println(MenuOptions.LIST_ENROLLMENTS_BY_STUDENT_ID);
            System.out.println(MenuOptions.CANCEL_ENROLLMENT_BY_ID);
            System.out.println(MenuOptions.COMPLETE_ENROLLMENT_BY_ID);
            System.out.println("8. " + MenuOptions.GO_BACK_TO_MAIN_MENU);
            System.out.print("Please enter your sub-choice (1-8): ");

            subChoice = getUserChoice(scanner);
            System.out.println();

            switch (subChoice) {
                case 1:
                    scanner.nextLine();
                    try {
                        createEnrollment(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    listEnrollments();
                    break;
                case 3:
                    scanner.nextLine();
                    try {
                        getEnrollmentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    try {
                        removeEnrollmentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    try {
                        listEnrollmentsByStudentId(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    try {
                        cancelEnrollmentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 7:
                    scanner.nextLine();
                    try {
                        completeEnrollmentById(scanner);
                    } catch (CancelInputException e) {
                        System.out.println(e);
                    }
                    break;
                case 8:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Error: Invalid sub-entry. Enter a number between 1 and 8.");
                    break;
            }
        } while (subChoice != 8);
    }

    private static void createEnrollment(Scanner scanner) {
        try {
            System.out.println("--------------------- ENTER ENROLLMENT DETAILS ----------------------");
            System.out.println("(Type 'cancel' to return to menu, or 'exit' to quit the app entirely)");
            System.out.println("---------------------------------------------------------------------");

            int studentId = -1;
            while (studentId == -1) {
                System.out.print("Enter Student Id: ");
                studentId = Integer.parseInt(readSafeInput(scanner));
                Student student = studentService.searchStudentById(studentId);
                if (student == null) {
                    System.out.println("============================ Student with id " + studentId + " not found. Please enter valid student id ============================");
                    studentId = -1;
                }
            }

            int courseId = -1;
            while (courseId == -1) {
                System.out.print("Enter Course Id: ");
                courseId = Integer.parseInt(readSafeInput(scanner));
                Course course = courseService.searchCourseById(courseId);
                if (course == null) {
                    System.out.println("============================ Course with id " + courseId + " not found. Please enter valid course id ============================");
                    courseId = -1;
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate validDate = null;

            // Loop until valid data is supplied
            while (validDate == null) {
                System.out.print("Enter Date of Enrollment in dd-MM-yyy format: ");
                String userInput = readSafeInput(scanner);

                try {
                    // Attempt to parse the input string
                    validDate = LocalDate.parse(userInput, formatter);
                } catch (DateTimeParseException e) {
                    // Handle invalid formats or wrong dates (e.g., 30-02-2026)
                    System.out.println("============================ Error: Invalid date or incorrect format. Please try again in this format: dd-MM-yyyy, example: 28-12-2026 ============================");
                }
            }

            enrollmentService.addEnrollment(studentId, courseId, validDate);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        }
    }

    private static void listEnrollments() {
        try {
            enrollmentService.listEnrollments();
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void getEnrollmentById(Scanner scanner) {
        try {
            System.out.println("Enter enrollment id to search for: ");
            int enrollmentId = Integer.parseInt(readSafeInput(scanner));
            enrollmentService.searchEnrollmentById(enrollmentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void removeEnrollmentById(Scanner scanner) {
        try {
            System.out.println("Enter enrollment id to remove: ");
            int enrollmentId = Integer.parseInt(readSafeInput(scanner));
            enrollmentService.removeEnrollment(enrollmentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void listEnrollmentsByStudentId(Scanner scanner) {
        try {
            System.out.println("Enter student id to list his enrollments: ");
            int studentId = Integer.parseInt(readSafeInput(scanner));
            enrollmentService.listEnrollmentByStudent(studentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void cancelEnrollmentById(Scanner scanner) {
        try {
            System.out.println("Enter enrollment id to cancel: ");
            int enrollmentId = Integer.parseInt(readSafeInput(scanner));
            enrollmentService.cancelEnrollment(enrollmentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static void completeEnrollmentById(Scanner scanner) {
        try {
            System.out.println("Enter enrollment id to complete: ");
            int enrollmentId = Integer.parseInt(readSafeInput(scanner));
            enrollmentService.completeEnrollment(enrollmentId);
        } catch (NumberFormatException e) {
            System.out.println("============================ Invalid number entered: " + e + " ============================");
        } catch (EmptyDataException e) {
            System.out.println(e);
        }
    }

    private static String readSafeInput(Scanner scanner) {
        String input = scanner.nextLine().trim();

        // Globally check for exit strings (case-insensitive)
        if (input.equalsIgnoreCase("exit")) {
            throw new SystemExitException("System exit triggered by user.");
        }
        if (input.equalsIgnoreCase("cancel")) {
            throw new CancelInputException("Aborted, returning to sub-menu");
        }

        return input;
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
