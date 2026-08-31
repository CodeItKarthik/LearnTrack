package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static final ArrayList<Student> students = new ArrayList<>();

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static List<Student> listStudents() {
        return students;
    }

    public static void removeStudent(Student student) {
        students.remove(student);
    }

    public static Student searchStudentById(int id) {
        Student studentSearched = null;
        for (Student student : students) {
            if (student.getId() == id) {
                studentSearched = student;
                break;
            }
        }

        return studentSearched;
    }

    public static void updateStudent(int index, Student student) {
        students.set(index, student);
    }

}
