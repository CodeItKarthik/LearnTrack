package com.airtribe.learntrack.utils;

import java.util.regex.Matcher;

import static com.airtribe.learntrack.constants.AppConstants.EMAIL_PATTERN;
import static com.airtribe.learntrack.constants.AppConstants.NAME_PATTERN;

public class InputValidator {

    /**
     * Validates provided string is neither null nor blank.
     * @param str The string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotBlank(String str) {
        if (str == null || str.isBlank()) {
            System.out.println("Please enter valid last name.");
            return false;
        }
        return true;
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
    public static boolean isValidFirstName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            System.out.println("Please enter valid first name.");
            return false;
        }

        if (studentName.length() < 2 || studentName.length() > 50) {
            System.out.println("Please enter valid first name.");
            return false;
        }

        if (!NAME_PATTERN.matcher(studentName).matches()) {
            System.out.println("Please enter valid first name.");
            return false;
        }

        return true;
    }

    /**
     * Basic validations for provided name.
     * @param courseName The name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            System.out.println("Please enter valid course name.");
            return false;
        }

        if (courseName.length() < 2 || courseName.length() > 50) {
            System.out.println("Please enter valid course name.");
            return false;
        }

        return true;
    }

    /**
     * Validate course description is within 200 chars.
     * @param courseDesc Description of course to validate
     * @return true if valid, false otherwise
     */
    public static boolean descriptionLengthCheck(String courseDesc) {
        if (courseDesc.length() <= 200) {
            return true;
        }
        System.out.println("Course description should be within 200 characters.");
        return false;
    }

    /**
     * Validates course duration is within bounds.
     * @param duration Course duration to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDuration(int duration) {
        if (duration > 0 && duration <= 53) {
            return true;
        }
        System.out.println("Please enter valid course duration");
        return false;
    }

}
