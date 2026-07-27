package com.airtribe.learntrack.utils;

import com.airtribe.learntrack.constants.AppConstants;

public class IdGenerator {

    private static int studentIdCounter = AppConstants.INITIAL_COUNT_VALUE;

    private static int trainerIdCounter = AppConstants.INITIAL_COUNT_VALUE;

    private static int courseIdCounter = AppConstants.INITIAL_COUNT_VALUE;

    private static int enrollmentIdCounter = AppConstants.INITIAL_COUNT_VALUE;

    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

    public static int getNextTrainerId() {
        return ++trainerIdCounter;
    }

}
