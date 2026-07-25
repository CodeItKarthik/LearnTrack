package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exceptions.DuplicateException;
import com.airtribe.learntrack.exceptions.EmptyDataException;
import com.airtribe.learntrack.exceptions.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.utils.IdGenerator;

import java.util.List;

public class CourseService {

    CourseRepository courseRepository = new CourseRepository();

    public void addCourse(String courseName, String description, int durationInWeeks) {
        List<Course> courses = courseRepository.listCourses();
        try {
            for (Course course : courses) {
                if (courseName.equalsIgnoreCase(course.getCourseName())) {
                    throw new DuplicateException("============================ There is already a course with the given name: " + courseName + ". Please check and try again ============================");
                }
            }

            int id = IdGenerator.getNextCourseId();
            Course course;
            if (description.isBlank()) {
                course = new Course(id, courseName, durationInWeeks, true);
            } else {
                course = new Course(id, courseName, description, durationInWeeks, true);
            }
            courseRepository.addCourse(course);
            System.out.println("============================ Course added successfully ============================");
        } catch (DuplicateException e) {
            System.out.println(e);
        }
    }

    public void removeCourse(int id) {
        List<Course> courses = courseRepository.listCourses();
        if (!courses.isEmpty()) {
            Course courseToDelete = null;
            for (Course course : courses) {
                if (course.getId() == id) {
                    courseToDelete = course;
                    break;
                }
            }

            try {
                if (courseToDelete != null) {
                    courseRepository.removeCourse(courseToDelete);
                    System.out.println("============================ Course with id: " + id + " removed successfully ============================");
                } else {
                    throw new EntityNotFoundException("============================ Course with id " + id + " not found ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no courses available ============================");
        }
    }

    public void removeCourse(String courseName) {
        List<Course> courses = courseRepository.listCourses();
        if (!courses.isEmpty()) {
            Course courseToDelete = null;

            for (Course course : courses) {
                if (courseName.equalsIgnoreCase(course.getCourseName())) {
                    courseToDelete = course;
                    break;
                }
            }

            try {
                if (courseToDelete != null) {
                    courseRepository.removeCourse(courseToDelete);
                    System.out.println("============================ Course with name: " + courseName + " removed successfully ============================");
                } else {
                    throw new EntityNotFoundException("============================ Course with name " + courseName + " not found ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no courses available ============================");
        }
    }

    public void listCourses() {
        List<Course> courses = courseRepository.listCourses();
        if (courses.isEmpty()) {
            throw new EmptyDataException("============================ There are no courses to display ============================");
        } else {
            System.out.println("============================ List of Courses ============================");
            courses.forEach(System.out::println);
        }
    }

    public Course searchCourseById(int id) {
        return courseRepository.searchCourseById(id);
    }

    public void deactivateCourse(int id) {
        List<Course> courses = courseRepository.listCourses();
        if (!courses.isEmpty()) {
            Course courseToDeactivate = null;
            int indexToUpdate = 0;

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getId() == id) {
                    courseToDeactivate = courses.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (courseToDeactivate == null) {
                    throw new EntityNotFoundException("============================ Course with id " + id + " not found ============================");
                } else {
                    courseToDeactivate.setActive(false);
                    courseRepository.updateCourse(indexToUpdate, courseToDeactivate);
                    System.out.println("============================ Course with id: " + id + " deactivated successfully ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no courses available ============================");
        }
    }

    public void activateCourse(int id) {
        List<Course> courses = courseRepository.listCourses();
        if (!courses.isEmpty()) {
            Course courseToActivate = null;
            int indexToUpdate = 0;

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getId() == id) {
                    courseToActivate = courses.get(i);
                    indexToUpdate = i;
                    break;
                }
            }

            try {
                if (courseToActivate == null) {
                    throw new EntityNotFoundException("============================ Course with id " + id + " not found ============================");
                } else {
                    courseToActivate.setActive(true);
                    courseRepository.updateCourse(indexToUpdate, courseToActivate);
                    System.out.println("============================ Course with id: " + id + " activated successfully ============================");
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException("============================ There are no courses available ============================");
        }
    }

//    public void updateCourse(int id, String courseName, String description, int durationInWeeks) {
//        Course courseToUpdate = null;
//        int indexToUpdate = 0;
//
//        for (int i = 0; i < courses.size(); i++) {
//            if (courses.get(i).getId() == id) {
//                courseToUpdate = courses.get(i);
//                indexToUpdate = i;
//                break;
//            }
//        }
//
//        try {
//            if (courseToUpdate == null) {
//                throw new EntityNotFoundException("Course with id " + id + " not found.");
//            } else {
//                courseToUpdate.setCourseName(courseName);
//                courseToUpdate.setDescription(description);
//                courseToUpdate.setDurationInWeeks(durationInWeeks);
//                courses.set(indexToUpdate, courseToUpdate);
//            }
//        } catch (EntityNotFoundException e) {
//            System.out.println(e);
//        }
//    }

}
