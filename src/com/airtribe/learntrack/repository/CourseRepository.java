package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> listCourses() {
        return courses;
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Course searchCourseById(int id) {
        Course courseSearched = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                courseSearched = course;
                break;
            }
        }
        return courseSearched;
    }

    public void updateCourse(int index, Course course) {
        courses.set(index, course);
    }

}
