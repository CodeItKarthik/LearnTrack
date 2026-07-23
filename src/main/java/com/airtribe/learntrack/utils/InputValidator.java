package main.java.com.airtribe.learntrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\p{L}+([\\s'-]\\p{L}+)*$");
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates provided string is neither null nor blank.
     * @param str The string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    /**
     * Validates the format of an email ID.
     * @param email The email string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * Basic validations for provided student name.
     * @param studentName Student name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            return false;
        }

        if (studentName.length() < 2 || studentName.length() > 50) {
            return false;
        }

        return NAME_PATTERN.matcher(studentName).matches();
    }

    /**
     * Basic validations for provided name.
     * @param courseName The name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            return false;
        }

        return courseName.length() >= 2 && courseName.length() <= 50;
    }

    /**
     * Validate course description is within 200 chars.
     * @param courseDesc Description of course to validate
     * @return true if valid, false otherwise
     */
    public static boolean descriptionLengthCheck(String courseDesc) {
        return courseDesc.length() <= 200;
    }

    /**
     * Validates course duration is within bounds.
     * @param duration Course duration to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDuration(int duration) {
        return duration > 0 && duration <= 53;
    }

}
