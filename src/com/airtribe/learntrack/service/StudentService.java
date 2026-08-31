package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exceptions.BadRequestException;
import com.airtribe.learntrack.exceptions.EmptyDataException;
import com.airtribe.learntrack.exceptions.EntityNotFoundException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.utils.BatchGenerator;
import com.airtribe.learntrack.utils.IdGenerator;

import java.util.List;

import static com.airtribe.learntrack.constants.AppConstants.*;

public class StudentService {

    DisplayService displayService = new DisplayService();

    public void addStudent(String firstName, String lastName, String email) {
        int id = IdGenerator.getNextStudentId();
        String batch = BatchGenerator.generateBatchNumber();
        Student student = new Student(id, firstName, lastName, email, batch, BOOL_TRUE);
        StudentRepository.addStudent(student);
        System.out.println(EQUALS + " Student added successfully " + EQUALS);
        displayService.displayPerson(student);
    }

    public void removeStudent(int id) {
        List<Student> students = StudentRepository.listStudents();
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!students.isEmpty()) {
            if (!enrollments.isEmpty()) {
                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getStudentId() == id) {
                        throw new BadRequestException(EQUALS + " This student is currently enrolled for a course. Can't execute remove operation! " + EQUALS);
                    }
                }
            }
            Student studentToDelete = null;
            for (Student student : students) {
                if (student.getId() == id) {
                    studentToDelete = student;
                    break;
                }
            }

            try {
                if (studentToDelete != null) {
                    StudentRepository.removeStudent(studentToDelete);
                    System.out.println(EQUALS + " Student with id: " + id + " removed successfully " + EQUALS);
                } else {
                    throw new EntityNotFoundException(EQUALS + " Student with id " + id + " not found " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no students available " + EQUALS);
        }
    }

    public void listStudents() {
        List<Student> students = StudentRepository.listStudents();
        if (students.isEmpty()) {
            throw new EmptyDataException(EQUALS + " There are no students to display " + EQUALS);
        } else {
            System.out.println(EQUALS + " List of Students " + EQUALS);
            students.forEach(System.out::println);
        }
    }

    public Student searchStudentById(int id) {
        return StudentRepository.searchStudentById(id);
    }

    public void deactivateStudent(int id) {
        List<Student> students = StudentRepository.listStudents();
        if (!students.isEmpty()) {
            Student studentToDeactivate = null;
            int indexToUpdate = 0;

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    studentToDeactivate = students.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (studentToDeactivate == null) {
                    throw new EntityNotFoundException(EQUALS + " Student with id " + id + " not found " + EQUALS);
                } else {
                    studentToDeactivate.setActive(BOOL_FALSE);
                    StudentRepository.updateStudent(indexToUpdate, studentToDeactivate);
                    System.out.println(EQUALS + " Student with id: " + id + " deactivated successfully " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no students available " + EQUALS);
        }
    }

    public void activateStudent(int id) {
        List<Student> students = StudentRepository.listStudents();
        if (!students.isEmpty()) {
            Student studentToActivate = null;
            int indexToUpdate = 0;

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    studentToActivate = students.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (studentToActivate == null) {
                    throw new EntityNotFoundException(EQUALS + " Student with id " + id + " not found " + EQUALS);
                } else {
                    studentToActivate.setActive(BOOL_TRUE);
                    StudentRepository.updateStudent(indexToUpdate, studentToActivate);
                    System.out.println(EQUALS + " Student with id: " + id + " activated successfully " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no students available " + EQUALS);
        }
    }

//    public void updateStudent(int id, String firstName, String lastName, String email, String batch) {
//        Student studentToUpdate = null;
//        int indexToUpdate = 0;
//
//        for (int i=0; i<students.size(); i++) {
//            if (students.get(i).getId() == id) {
//                studentToUpdate = students.get(i);
//                indexToUpdate = i;
//                break;
//            }
//        }
//
//        try {
//            if (studentToUpdate == null) {
//                throw new EntityNotFoundException("Student with id " + id + " not found.");
//            } else {
//                studentToUpdate.setFirstName(firstName);
//                studentToUpdate.setLastName(lastName);
//                studentToUpdate.setEmail(email);
//                studentToUpdate.setBatch(batch);
//                students.set(indexToUpdate, studentToUpdate);
//            }
//        } catch (EntityNotFoundException e) {
//            System.out.println(e);
//        }
//    }

}
