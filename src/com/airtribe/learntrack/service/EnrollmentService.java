package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.STATUSENUM;
import com.airtribe.learntrack.exceptions.DuplicateException;
import com.airtribe.learntrack.exceptions.EmptyDataException;
import com.airtribe.learntrack.exceptions.EntityNotFoundException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.utils.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.airtribe.learntrack.constants.AppConstants.EQUALS;

public class EnrollmentService {

    public void addEnrollment(int studentId, int courseId, LocalDate enrollmentDate) {
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        try {
            for (Enrollment enrollment : enrollments) {
                if (studentId == enrollment.getStudentId() && courseId == enrollment.getCourseId() && STATUSENUM.ACTIVE.equals(enrollment.getStatus())) {
                    throw new DuplicateException(EQUALS + " Active enrollment found for student with id " + studentId + " for course " + courseId + " " + EQUALS);
                }
            }

            int id = IdGenerator.getNextEnrollmentId();
            Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate, STATUSENUM.ACTIVE);
            EnrollmentRepository.addEnrollment(enrollment);
            System.out.println(EQUALS + " Enrollment completed successfully " + EQUALS);
        } catch (DuplicateException e) {
            System.out.println(e);
        }
    }

    public void removeEnrollment(int id) {
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            Enrollment enrollmentToDelete = null;

            for (Enrollment enrollment : enrollments) {
                if (enrollment.getId() == id) {
                    enrollmentToDelete = enrollment;
                    break;
                }
            }

            try {
                if (enrollmentToDelete != null) {
                    EnrollmentRepository.removeEnrollment(enrollmentToDelete);
                    System.out.println(EQUALS + " Enrollment with id: " + id + " removed successfully " + EQUALS);
                } else {
                    throw new EntityNotFoundException(EQUALS + " Enrollment with id " + id + " not found " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no enrollments available " + EQUALS);
        }
    }

    public void listEnrollments() {
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (enrollments.isEmpty()) {
            throw new EmptyDataException(EQUALS + " There are no enrollments to display " + EQUALS);
        } else {
            System.out.println(EQUALS + " List of Enrollments " + EQUALS);
            enrollments.forEach(System.out::println);
        }
    }

    public void listEnrollmentByStudent(int id) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudentId() == id) {
                    studentEnrollments.add(enrollment);
                }
            }

            try {
                if (studentEnrollments.isEmpty()) {
                    throw new EntityNotFoundException(EQUALS + " This student " + id + " is not enrolled in any courses " + EQUALS);
                } else {
                    System.out.println(EQUALS + " List of enrollments for student with id: " + id + " " + EQUALS);
                    studentEnrollments.forEach(System.out::println);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no enrollments available " + EQUALS);
        }
    }

    public Enrollment searchEnrollmentById(int id) {
        return EnrollmentRepository.searchEnrollmentById(id);
    }

    public void cancelEnrollment(int id) {
        Enrollment enrollmentToCancel = null;
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            int indexToUpdate = 0;

            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getId() == id) {
                    enrollmentToCancel = enrollments.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (enrollmentToCancel == null) {
                    throw new EntityNotFoundException(EQUALS + " Enrollment with id " + id + " not found " + EQUALS);
                } else {
                    enrollmentToCancel.setStatus(STATUSENUM.CANCELLED);
                    EnrollmentRepository.updateEnrollment(indexToUpdate, enrollmentToCancel);
                    System.out.println(EQUALS + " Enrollment with id " + id + " marked as cancelled " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no enrollments available " + EQUALS);
        }
    }

    public void completeEnrollment(int id) {
        Enrollment enrollmentToComplete = null;
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            int indexToUpdate = 0;

            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getId() == id) {
                    enrollmentToComplete = enrollments.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (enrollmentToComplete == null) {
                    throw new EntityNotFoundException(EQUALS + " Enrollment with id " + id + " not found " + EQUALS);
                } else {
                    enrollmentToComplete.setStatus(STATUSENUM.COMPLETED);
                    EnrollmentRepository.updateEnrollment(indexToUpdate, enrollmentToComplete);
                    System.out.println(EQUALS + " Enrollment with id " + id + " marked as completed " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no enrollments available " + EQUALS);
        }
    }

//    public void removeEnrollment(int studentId, int courseId) {
//        Enrollment enrollmentToDelete = null;
//
//        for (Enrollment enrollment : enrollments) {
//            if (studentId == enrollment.getStudentId() && courseId == enrollment.getCourseId()) {
//                enrollmentToDelete = enrollment;
//                break;
//            }
//        }
//
//        try {
//            if (enrollmentToDelete != null) {
//                enrollments.remove(enrollmentToDelete);
//            } else {
//                throw new EntityNotFoundException("Enrollment of student " + studentId + " for the course " + courseId + " not found.");
//            }
//        } catch (EntityNotFoundException e) {
//            System.out.println(e);
//        }
//    }

//    public void updateEnrollment(int id, int studentId, int courseId, LocalDate enrollmentDate) {
//        Enrollment enrollmentToUpdate = null;
//        int indexToUpdate = 0;
//
//        for (int i=0; i<enrollments.size(); i++) {
//            if (enrollments.get(i).getId() == id) {
//                enrollmentToUpdate = enrollments.get(i);
//                indexToUpdate = i;
//                break;
//            }
//        }
//
//        try {
//            if (enrollmentToUpdate == null) {
//                throw new EntityNotFoundException("Enrollment with id " + id + " not found.");
//            } else {
//                enrollmentToUpdate.setStudentId(studentId);
//                enrollmentToUpdate.setCourseId(courseId);
//                enrollmentToUpdate.setEnrollmentDate(enrollmentDate);
//                enrollments.set(indexToUpdate, enrollmentToUpdate);
//            }
//        } catch (EntityNotFoundException e) {
//            System.out.println(e);
//        }
//    }

}
