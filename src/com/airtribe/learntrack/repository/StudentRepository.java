package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> listStudents() {
        return students;
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public Student searchStudentById(int id) {
        Student studentSearched = null;
        for (Student student : students) {
            if (student.getId() == id) {
                studentSearched = student;
                break;
            }
        }

        return studentSearched;
    }

    public void updateStudent(int index, Student student) {
        students.set(index, student);
    }

}
