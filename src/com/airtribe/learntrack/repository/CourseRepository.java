package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private static final ArrayList<Course> courses = new ArrayList<>();

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static List<Course> listCourses() {
        return courses;
    }

    public static void removeCourse(Course course) {
        courses.remove(course);
    }

    public static Course searchCourseById(int id) {
        Course courseSearched = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                courseSearched = course;
                break;
            }
        }
        return courseSearched;
    }

    public static void updateCourse(int index, Course course) {
        courses.set(index, course);
    }

}
