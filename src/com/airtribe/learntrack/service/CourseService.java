package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.exceptions.BadRequestException;
import com.airtribe.learntrack.exceptions.DuplicateException;
import com.airtribe.learntrack.exceptions.EmptyDataException;
import com.airtribe.learntrack.exceptions.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.utils.IdGenerator;

import java.util.List;

import static com.airtribe.learntrack.constants.AppConstants.*;

public class CourseService {

    public void addCourse(String courseName, String description, int durationInWeeks) {
        List<Course> courses = CourseRepository.listCourses();
        try {
            for (Course course : courses) {
                if (courseName.equalsIgnoreCase(course.getCourseName())) {
                    throw new DuplicateException(EQUALS + " There is already a course with the given name: " + courseName + ". Please check and try again " + EQUALS);
                }
            }

            int id = IdGenerator.getNextCourseId();
            Course course;
            if (description.isBlank()) {
                course = new Course(id, courseName, durationInWeeks, BOOL_TRUE);
            } else {
                course = new Course(id, courseName, description, durationInWeeks, BOOL_TRUE);
            }
            CourseRepository.addCourse(course);
            System.out.println(EQUALS + " Course added successfully " + EQUALS);
        } catch (DuplicateException e) {
            System.out.println(e);
        }
    }

    public void removeCourse(int id) {
        List<Course> courses = CourseRepository.listCourses();
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!courses.isEmpty()) {
            if (!enrollments.isEmpty()) {
                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getStudentId() == id) {
                        throw new BadRequestException(EQUALS + " This course is currently subscribed by one or more Students. Can't execute remove operation! " + EQUALS);
                    }
                }
            }
            Course courseToDelete = null;
            for (Course course : courses) {
                if (course.getId() == id) {
                    courseToDelete = course;
                    break;
                }
            }

            try {
                if (courseToDelete != null) {
                    CourseRepository.removeCourse(courseToDelete);
                    System.out.println(EQUALS + " Course with id: " + id + " removed successfully " + EQUALS);
                } else {
                    throw new EntityNotFoundException(EQUALS + " Course with id " + id + " not found " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no courses available " + EQUALS);
        }
    }

    public void removeCourse(String courseName) {
        List<Course> courses = CourseRepository.listCourses();
        List<Enrollment> enrollments = EnrollmentRepository.listEnrollments();
        if (!courses.isEmpty()) {
            if (!enrollments.isEmpty()) {
                for (Enrollment enrollment : enrollments) {
                    Course course = searchCourseById(enrollment.getCourseId());
                    if (courseName.equalsIgnoreCase(course.getCourseName())) {
                        throw new BadRequestException(EQUALS + " This course is currently subscribed by one or more Students. Can't execute remove operation! " + EQUALS);
                    }
                }
            }
            Course courseToDelete = null;
            for (Course course : courses) {
                if (courseName.equalsIgnoreCase(course.getCourseName())) {
                    courseToDelete = course;
                    break;
                }
            }

            try {
                if (courseToDelete != null) {
                    CourseRepository.removeCourse(courseToDelete);
                    System.out.println(EQUALS + " Course with name: " + courseName + " removed successfully " + EQUALS);
                } else {
                    throw new EntityNotFoundException(EQUALS + " Course with name " + courseName + " not found " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no courses available " + EQUALS);
        }
    }

    public void listCourses() {
        List<Course> courses = CourseRepository.listCourses();
        if (courses.isEmpty()) {
            throw new EmptyDataException(EQUALS + " There are no courses to display " + EQUALS);
        } else {
            System.out.println(EQUALS + " List of Courses " + EQUALS);
            courses.forEach(System.out::println);
        }
    }

    public Course searchCourseById(int id) {
        return CourseRepository.searchCourseById(id);
    }

    public void deactivateCourse(int id) {
        List<Course> courses = CourseRepository.listCourses();
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
                    throw new EntityNotFoundException(EQUALS + " Course with id " + id + " not found " + EQUALS);
                } else {
                    courseToDeactivate.setActive(BOOL_FALSE);
                    CourseRepository.updateCourse(indexToUpdate, courseToDeactivate);
                    System.out.println(EQUALS + " Course with id: " + id + " deactivated successfully " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no courses available " + EQUALS);
        }
    }

    public void activateCourse(int id) {
        List<Course> courses = CourseRepository.listCourses();
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
                    throw new EntityNotFoundException(EQUALS + " Course with id " + id + " not found " + EQUALS);
                } else {
                    courseToActivate.setActive(BOOL_TRUE);
                    CourseRepository.updateCourse(indexToUpdate, courseToActivate);
                    System.out.println(EQUALS + " Course with id: " + id + " activated successfully " + EQUALS);
                }
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        } else {
            throw new EmptyDataException(EQUALS + " There are no courses available " + EQUALS);
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
