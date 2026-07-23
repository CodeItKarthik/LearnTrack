package main.java.com.airtribe.learntrack.service;

import main.java.com.airtribe.learntrack.entity.Course;
import main.java.com.airtribe.learntrack.exceptions.DuplicateException;
import main.java.com.airtribe.learntrack.exceptions.EntityNotFoundException;
import main.java.com.airtribe.learntrack.utils.IdGenerator;

import java.util.ArrayList;

public class CourseService {

    ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(String courseName, String description, int durationInWeeks) {
        for (Course course : courses) {
            if (courseName.equalsIgnoreCase(course.getCourseName())) {
                throw new DuplicateException("There is already a course with the given name : " + courseName + ". Please check and try again.");
            }
        }

        int id = IdGenerator.getNextCourseId();
        Course course;
        if (description.isBlank()) {
            course = new Course(id, courseName, durationInWeeks, true);
        } else {
            course = new Course(id, courseName, description, durationInWeeks, true);
        }
        courses.add(course);
        System.out.println("Course added successfully with id : " + id);
    }

    public void removeCourse(int id) {
        Course courseToDelete = null;

        for (Course course : courses) {
            if (course.getId() == id) {
                courseToDelete = course;
                break;
            }
        }

        if (courseToDelete != null) {
            courses.remove(courseToDelete);
        } else {
            throw new EntityNotFoundException("Course with id " + id + " not found.");
        }
    }

    public void removeCourse(String courseName) {
        Course courseToDelete = null;

        for (Course course : courses) {
            if (courseName.equalsIgnoreCase(course.getCourseName())) {
                courseToDelete = course;
                break;
            }
        }

        if (courseToDelete != null) {
            courses.remove(courseToDelete);
        } else {
            throw new EntityNotFoundException("Course with name " + courseName + " not found.");
        }
    }

    public void updateCourse(int id, String courseName, String description, int durationInWeeks) {
        Course courseToUpdate = null;
        int indexToUpdate = 0;

        for (int i=0; i<courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                courseToUpdate = courses.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (courseToUpdate == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found.");
        } else {
            courseToUpdate.setCourseName(courseName);
            courseToUpdate.setDescription(description);
            courseToUpdate.setDurationInWeeks(durationInWeeks);
            courses.set(indexToUpdate, courseToUpdate);
        }
    }

    public void listCourses() {
        if (courses.isEmpty()) {
            System.out.println("There are no courses to display.");
        } else {
            System.out.println(courses);
        }
    }

    public void searchCourseById(int id) {
        Course courseSearched = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                courseSearched = course;
                break;
            }
        }

        if (courseSearched == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found.");
        } else {
            System.out.println(courseSearched);
        }
    }

    public void deactivateCourse(int id) {
        Course courseToDeactivate = null;
        int indexToUpdate = 0;

        for (int i=0; i<courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                courseToDeactivate = courses.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (courseToDeactivate == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found.");
        } else {
            courseToDeactivate.setActive(false);
            courses.set(indexToUpdate, courseToDeactivate);
        }
    }

    public void activateCourse(int id) {
        Course courseToActivate = null;
        int indexToUpdate = 0;

        for (int i=0; i<courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                courseToActivate = courses.get(i);
                indexToUpdate = i;
                break;
            }
        }

        if (courseToActivate == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found.");
        } else {
            courseToActivate.setActive(true);
            courses.set(indexToUpdate, courseToActivate);
        }
    }

}
