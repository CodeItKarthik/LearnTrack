package main.java.com.airtribe.learntrack.service;

import main.java.com.airtribe.learntrack.entity.Student;
import main.java.com.airtribe.learntrack.exceptions.EntityNotFoundException;
import main.java.com.airtribe.learntrack.utils.BatchGenerator;
import main.java.com.airtribe.learntrack.utils.IdGenerator;

import java.util.ArrayList;

public class StudentService {

    ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String firstName, String lastName, String email) {
        int id = IdGenerator.getNextStudentId();
        String batch = BatchGenerator.generateBatchNumber();
        Student student = new Student(id, firstName, lastName, email, batch, true);
        students.add(student);
        System.out.println("Student added successfully with id : " + id);
    }

    public void removeStudent(int id) {
        Student studentToDelete = null;

        for (Student student : students) {
            if (student.getId() == id) {
                studentToDelete = student;
                break;
            }
        }

        if (studentToDelete != null) {
            students.remove(studentToDelete);
        } else {
            throw new EntityNotFoundException("Student with id " + id + " not found.");
        }
    }

    public void removeStudent(String firstName, String lastName) {
        Student studentToDelete = null;

        for (Student student : students) {
            if (firstName.equalsIgnoreCase(student.getFirstName())
                    && lastName.equalsIgnoreCase(student.getLastName())) {
                studentToDelete = student;
                break;
            }
        }

        if (studentToDelete != null) {
            students.remove(studentToDelete);
        } else {
            throw new EntityNotFoundException("Student with name " + firstName + lastName + " not found.");
        }
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch) {
        Student studentToUpdate = null;
        int indexToUpdate = 0;

        for (int i=0; i<students.size(); i++) {
            if (students.get(i).getId() == id) {
                studentToUpdate = students.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (studentToUpdate == null) {
            throw new EntityNotFoundException("Student with id " + id + " not found.");
        } else {
            studentToUpdate.setFirstName(firstName);
            studentToUpdate.setLastName(lastName);
            studentToUpdate.setEmail(email);
            studentToUpdate.setBatch(batch);
            students.set(indexToUpdate, studentToUpdate);
        }
    }

    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println("There are no students to display.");
        } else {
            System.out.println(students);
        }
    }

    public void searchStudentById(int id) {
        Student studentSearched = null;
        for (Student student : students) {
            if (student.getId() == id) {
                studentSearched = student;
                break;
            }
        }

        if (studentSearched == null) {
            throw new EntityNotFoundException("Student with id " + id + " not found.");
        } else {
            System.out.println(studentSearched);
        }
    }

    public void deactivateStudent(int id) {
        Student studentToDeactivate = null;
        int indexToUpdate = 0;

        for (int i=0; i<students.size(); i++) {
            if (students.get(i).getId() == id) {
                studentToDeactivate = students.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (studentToDeactivate == null) {
            throw new EntityNotFoundException("Student with id " + id + " not found.");
        } else {
            studentToDeactivate.setActive(false);
            students.set(indexToUpdate, studentToDeactivate);
        }
    }

}
