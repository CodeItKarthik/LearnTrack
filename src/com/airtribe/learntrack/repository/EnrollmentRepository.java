package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    ArrayList<Enrollment> enrollments = new ArrayList<>();

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public List<Enrollment> listEnrollments() {
        return enrollments;
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    public Enrollment searchEnrollmentById(int id) {
        Enrollment enrollmentSearched = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                enrollmentSearched = enrollment;
                break;
            }
        }
        return enrollmentSearched;
    }

    public void updateEnrollment(int index, Enrollment enrollment) {
        enrollments.set(index, enrollment);
    }
}
