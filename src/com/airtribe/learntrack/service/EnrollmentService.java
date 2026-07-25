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

public class EnrollmentService {

    EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    public void addEnrollment(int studentId, int courseId, LocalDate enrollmentDate) {
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
        try {
            for (Enrollment enrollment : enrollments) {
                if (studentId == enrollment.getStudentId() && courseId == enrollment.getCourseId()) {
                    throw new DuplicateException("============================ This student with id " + studentId + " is already enrolled for this course " + courseId + " ============================");
                }
            }

            int id = IdGenerator.getNextEnrollmentId();
            Enrollment enrollment = new Enrollment(id, studentId, courseId, enrollmentDate, STATUSENUM.ACTIVE);
            enrollmentRepository.addEnrollment(enrollment);
            System.out.println("============================ Enrollment completed successfully ============================");
        } catch (DuplicateException e) {
            System.out.println(e);
        }
    }

    public void removeEnrollment(int id) {
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
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
                    enrollmentRepository.removeEnrollment(enrollmentToDelete);
                    System.out.println("============================ Enrollment with id: " + id + " removed successfully ============================");
                } else {
                    throw new EntityNotFoundException("============================ Enrollment with id " + id + " not found ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no enrollments available ============================");
        }
    }

    public void listEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
        if (enrollments.isEmpty()) {
            throw new EmptyDataException("============================ There are no enrollments to display ============================");
        } else {
            System.out.println("============================ List of Enrollments ============================");
            enrollments.forEach(System.out::println);
        }
    }

    public void listEnrollmentByStudent(int id) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudentId() == id) {
                    studentEnrollments.add(enrollment);
                }
            }

            try {
                if (studentEnrollments.isEmpty()) {
                    throw new EntityNotFoundException("============================ This student " + id + " is not enrolled in any courses ============================");
                } else {
                    System.out.println("====================== List of enrollments for student with id: " + id + " ======================");
                    studentEnrollments.forEach(System.out::println);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no enrollments available ============================");
        }
    }

    public void searchEnrollmentById(int id) {
        Enrollment enrollmentSearched = null;
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
        if (!enrollments.isEmpty()) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getId() == id) {
                    enrollmentSearched = enrollment;
                    break;
                }
            }

            try {
                if (enrollmentSearched == null) {
                    throw new EntityNotFoundException("============================ Enrollment with id " + id + " not found ============================");
                } else {
                    System.out.println("====================== Enrollment details for id: " + id + " ======================");
                    System.out.println(enrollmentSearched);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no enrollments available ============================");
        }
    }

    public void cancelEnrollment(int id) {
        Enrollment enrollmentToCancel = null;
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
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
                    throw new EntityNotFoundException("============================ Enrollment with id " + id + " not found ============================");
                } else {
                    enrollmentToCancel.setStatus(STATUSENUM.CANCELLED);
                    enrollmentRepository.updateEnrollment(indexToUpdate, enrollmentToCancel);
                    System.out.println("============================ Enrollment with id " + id + " marked as cancelled ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no enrollments available ============================");
        }
    }

    public void completeEnrollment(int id) {
        Enrollment enrollmentToComplete = null;
        List<Enrollment> enrollments = enrollmentRepository.listEnrollments();
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
                    throw new EntityNotFoundException("============================ Enrollment with id " + id + " not found ============================");
                } else {
                    enrollmentToComplete.setStatus(STATUSENUM.COMPLETED);
                    enrollmentRepository.updateEnrollment(indexToUpdate, enrollmentToComplete);
                    System.out.println("============================ Enrollment with id " + id + " marked as completed ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no enrollments available ============================");
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
