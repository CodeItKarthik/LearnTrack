package main.java.com.airtribe.learntrack.service;

import main.java.com.airtribe.learntrack.entity.Enrollment;
import main.java.com.airtribe.learntrack.entity.STATUSENUM;
import main.java.com.airtribe.learntrack.exceptions.DuplicateException;
import main.java.com.airtribe.learntrack.exceptions.EntityNotFoundException;
import main.java.com.airtribe.learntrack.utils.IdGenerator;

import java.util.ArrayList;
import java.util.Date;

public class EnrollmentService {

    ArrayList<Enrollment> enrollments = new ArrayList<>();

    public void addEnrollment(int studentId, int courseId, Date enrollmentDate) {
        for (Enrollment enrollment : enrollments) {
            if (studentId == enrollment.getStudentId() && courseId == enrollment.getCourseId()) {
                throw new DuplicateException("This student with id " + studentId + " is already enrolled for this course " + courseId);
            }
        }

        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate, STATUSENUM.ACTIVE);
        enrollments.add(enrollment);
        System.out.println("Student enrolled successfully for the course.");
    }

    public void removeEnrollment(int id) {
        Enrollment enrollmentToDelete = null;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                enrollmentToDelete = enrollment;
                break;
            }
        }

        if (enrollmentToDelete != null) {
            enrollments.remove(enrollmentToDelete);
        } else {
            throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
        }
    }

    public void removeEnrollment(int studentId, int courseId) {
        Enrollment enrollmentToDelete = null;

        for (Enrollment enrollment : enrollments) {
            if (studentId == enrollment.getStudentId() && courseId == enrollment.getCourseId()) {
                enrollmentToDelete = enrollment;
                break;
            }
        }

        if (enrollmentToDelete != null) {
            enrollments.remove(enrollmentToDelete);
        } else {
            throw new EntityNotFoundException("Enrollment of student " + studentId + " for the course " + courseId + " not found.");
        }
    }

    public void updateEnrollment(int id, int studentId, int courseId, Date enrollmentDate) {
        Enrollment enrollmentToUpdate = null;
        int indexToUpdate = 0;

        for (int i=0; i<enrollments.size(); i++) {
            if (enrollments.get(i).getId() == id) {
                enrollmentToUpdate = enrollments.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (enrollmentToUpdate == null) {
            throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
        } else {
            enrollmentToUpdate.setStudentId(studentId);
            enrollmentToUpdate.setCourseId(courseId);
            enrollmentToUpdate.setEnrollmentDate(enrollmentDate);
            enrollments.set(indexToUpdate, enrollmentToUpdate);
        }
    }

    public void listCourses() {
        if (enrollments.isEmpty()) {
            System.out.println("There are no courses to display.");
        } else {
            System.out.println(enrollments);
        }
    }

    public void searchEnrollmentById(int id) {
        Enrollment enrollmentSearched = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                enrollmentSearched = enrollment;
                break;
            }
        }

        if (enrollmentSearched == null) {
            throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
        } else {
            System.out.println(enrollmentSearched);
        }
    }

    public void cancelEnrollment(int id) {
        Enrollment enrollmentToCancel = null;
        int indexToUpdate = 0;

        for (int i=0; i<enrollments.size(); i++) {
            if (enrollments.get(i).getId() == id) {
                enrollmentToCancel = enrollments.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (enrollmentToCancel == null) {
            throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
        } else {
            enrollmentToCancel.setStatus(STATUSENUM.CANCELLED);
            enrollments.set(indexToUpdate, enrollmentToCancel);
        }
    }

    public void completeEnrollment(int id) {
        Enrollment enrollmentToComplete = null;
        int indexToUpdate = 0;

        for (int i=0; i<enrollments.size(); i++) {
            if (enrollments.get(i).getId() == id) {
                enrollmentToComplete = enrollments.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (enrollmentToComplete == null) {
            throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
        } else {
            enrollmentToComplete.setStatus(STATUSENUM.COMPLETED);
            enrollments.set(indexToUpdate, enrollmentToComplete);
        }
    }

}
