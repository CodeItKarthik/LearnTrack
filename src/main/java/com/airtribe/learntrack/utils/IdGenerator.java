package main.java.com.airtribe.learntrack.utils;

public class IdGenerator {

    private static int studentIdCounter = 0;

    private static int courseIdCounter = 0;

    private static int enrollmentIdCounter = 0;

    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

}
