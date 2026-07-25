package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exceptions.EmptyDataException;
import com.airtribe.learntrack.exceptions.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.utils.BatchGenerator;
import com.airtribe.learntrack.utils.IdGenerator;

import java.util.List;

public class StudentService {

    StudentRepository studentRepository = new StudentRepository();

    public void addStudent(String firstName, String lastName, String email) {
        int id = IdGenerator.getNextStudentId();
        String batch = BatchGenerator.generateBatchNumber();
        Student student = new Student(id, firstName, lastName, email, batch, true);
        studentRepository.addStudent(student);
        System.out.println("============================ Student added successfully ============================");
    }

    public void removeStudent(int id) {
        List<Student> students = studentRepository.listStudents();
        if (!students.isEmpty()) {
            Student studentToDelete = null;
            for (Student student : students) {
                if (student.getId() == id) {
                    studentToDelete = student;
                    break;
                }
            }

            try {
                if (studentToDelete != null) {
                    studentRepository.removeStudent(studentToDelete);
                    System.out.println("============================ Student with id: " + id + " removed successfully ============================");
                } else {
                    throw new EntityNotFoundException("============================ Student with id " + id + " not found ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no students available ============================");
        }
    }

    public void listStudents() {
        List<Student> students = studentRepository.listStudents();
        if (students.isEmpty()) {
            throw new EmptyDataException("============================ There are no students to display ============================");
        } else {
            System.out.println("============================ List of Students ============================");
            students.forEach(System.out::println);
        }
    }

    public Student searchStudentById(int id) {
        return studentRepository.searchStudentById(id);
    }

    public void deactivateStudent(int id) {
        List<Student> students = studentRepository.listStudents();
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
                    throw new EntityNotFoundException("============================ Student with id " + id + " not found ============================");
                } else {
                    studentToDeactivate.setActive(false);
                    studentRepository.updateStudent(indexToUpdate, studentToDeactivate);
                    System.out.println("============================ Student with id: " + id + " deactivated successfully ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no students available ============================");
        }
    }

    public void activateStudent(int id) {
        List<Student> students = studentRepository.listStudents();
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
                    throw new EntityNotFoundException("============================ Student with id " + id + " not found ============================");
                } else {
                    studentToActivate.setActive(true);
                    studentRepository.updateStudent(indexToUpdate, studentToActivate);
                    System.out.println("============================ Student with id: " + id + " activated successfully ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no students available ============================");
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
