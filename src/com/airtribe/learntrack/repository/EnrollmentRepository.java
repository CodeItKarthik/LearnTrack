package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    private static final ArrayList<Enrollment> enrollments = new ArrayList<>();

    public static void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public static List<Enrollment> listEnrollments() {
        return enrollments;
    }

    public static void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    public static Enrollment searchEnrollmentById(int id) {
        Enrollment enrollmentSearched = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                enrollmentSearched = enrollment;
                break;
            }
        }
        return enrollmentSearched;
    }

    public static void updateEnrollment(int index, Enrollment enrollment) {
        enrollments.set(index, enrollment);
    }
}
