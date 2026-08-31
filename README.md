# LearnTrack - Student & Course Management System

LearnTrack is a modular, console-based Student & Course Management System built using **Core Java**. Designed with a strict focus on foundational software engineering principles, the application provides an in-memory platform for managing students, courses, and enrollments.

This project completely avoids advanced frameworks, external databases, concurrency, or complex streams. Instead, it serves as a pure sandbox to master core **object-oriented programming (OOP)**, structured package layouts, data encapsulation, dynamic collections, and clean command-line interface design.

## Core Feature Set

- **Student Management:** Add new Student to the system, list students and also search them by their id via structured tracking keys, hard delete or softly deactivate student profiles without compromising relational data records.
- **Course Control:** Manage active curriculum profiles, add new courses, list them all or search them by their id, hard delete courses by course id or course name and also dynamically toggle between activate and deactivate courses.
- **Enrollment Engine:** Bind unique Student and Course profiles with contextual state parameters `(ACTIVE, COMPLETED, CANCELLED)`, list all the enrollments or list them by id or even by student id, hard delete an enrollment and also update their status as `COMPLETED` or `CANCELLED`.
- **In-Memory Volatile Storage:** Uses Java's `ArrayList` collections to manage data lifecycles cleanly within runtime application memory.
- **Failure-Resistant Console Input:** Intercepts out-of-bounds menu indices, invalid data inputs by user, number parsing errors, and structural missing-record faults without terminating the system process thread.

## Installation & Compilation

### Step 1: Clone the Repository
Ensure a Java Development Kit (JDK 8 or higher) is configured on your local system path.
1. Clone the Code Repository: `git clone https://github.com/CodeItKarthik/LearnTrack.git`

### Step 2: Import into IntelliJ IDEA
1. Open IntelliJ IDEA.
2. On the welcome screen, click Open (or go to **File > Open** if a project is already open).
3. Navigate to the `LearnTrack` folder you just cloned.
4. Select the root folder and click **OK**.
5. If prompted, select **Trust Project**.

### Step 3: Configure the Project SDK
1. Go to **File > Project Structure** (or press `Ctrl+Alt+Shift+S` on Windows, `Cmd+;` on macOS).
2. Click on **Project** under the Project Settings sidebar.
3. Locate the **SDK** dropdown. If no JDK is selected, click it and choose your installed version (JDK 8 or higher).
4. Click **Apply** and then **OK**.

### Step 4: Run the Application
1. In the Project tool window on the left, expand the directories: `src > com > airtribe > learntrack`.
2. Right-click on the `Main.java` file.
3. Select **Run 'Main.main()'** from the context menu (or click the green play icon next to the `public static void main` method inside the file).

## Class Diagram

```mermaid
classDiagram
    %% --- Entities ---
    class Student {
        -int id
        -String firstName
        -String lastName
        -String email
        -String batch
        -boolean active
    }

    class Course {
        -int id
        -String courseName
        -String description
        -int durationInWeeks
        -boolean active
    }

    class Enrollment {
        -int id
        -int studentId
        -int courseId
        -LocalDate enrollmentDate
        -STATUSENUM status
    }

    class STATUSENUM {
        <<enumeration>>
        ACTIVE
        COMPLETED
        CANCELLED
    }

    Enrollment --> STATUSENUM : uses

    %% --- Repositories ---
    class StudentRepository {
        -ArrayList~Student~ students
        +addStudent(student: Student) void
        +listStudents() List~Student~
        +removeStudent(student: Student) void
        +searchStudentById(id: int) Student
        +updateStudent(index: int, student: Student) void
    }

    class CourseRepository {
        -ArrayList~Course~ courses
        +addCourse(course: Course) void
        +listCourses() List~Course~
        +removeCourse(course: Course) void
        +searchCourseById(id: int) Course
        +updateCourse(index: int, course: Course) void
    }

    class EnrollmentRepository {
        -ArrayList~Enrollment~ enrollments
        +addEnrollment(enrollment: Enrollment) void
        +listEnrollments() List~Enrollment~
        +removeEnrollment(enrollment: Enrollment) void
        +searchEnrollmentById(id: int) Enrollment
        +updateEnrollment(index: int, enrollment: Enrollment) void
    }

    %% Aggregation relationships
    StudentRepository o-- Student
    CourseRepository o-- Course
    EnrollmentRepository o-- Enrollment

    %% --- Services ---
    class StudentService {
        -StudentRepository studentRepository
        +addStudent(firstName: String, lastName: String, email: String) void
        +removeStudent(id: int) void
        +listStudents() List~Student~
        +searchStudentById(id: int) Student
        +deactivateStudent(id: int) void
        +activateStudent(id: int) void
    }

    class CourseService {
        -CourseRepository courseRepository
        +addCourse(courseName: String, description: String, durationInWeeks: int) void
        +removeCourse(id: int) void
        +removeCourse(name: String) void
        +listCourses() List~Course~
        +searchCourseById(id: int) Course
        +deactivateCourse(id: int) void
        +activateCourse(id: int) void
    }

    class EnrollmentService {
        -EnrollmentRepository enrollmentRepository
        +addEnrollment(studentId: int, courseId: int, enrollmentDate: LocalDate) void
        +removeEnrollment(id: int) void
        +listEnrollments() List~Enrollment~
        +listEnrollmentByStudent(studentId: int) List~Enrollment~
        +searchEnrollmentById(id: int) Enrollment
        +cancelEnrollment(id: int) void
        +completeEnrollment(id: int) void
    }

    %% Directed associations
    StudentService --> StudentRepository
    CourseService --> CourseRepository
    EnrollmentService --> EnrollmentRepository

    %% --- Main Interface ---
    class Main {
        -StudentService studentService
        -CourseService courseService
        -EnrollmentService enrollmentService
        -Scanner scanner
        +main(args: String[]) void
        -displayMainMenu() void
        -handleStudentManagement() void
        -handleCourseManagement() void
        -handleEnrollmentManagement() void
    }

    Main --> StudentService
    Main --> CourseService
    Main --> EnrollmentService

    %% --- Custom Exceptions ---
    class CancelInputException {
        <<Exception>>
    }
    class DuplicateException {
        <<Exception>>
    }
    class EmptyDataException {
        <<Exception>>
    }
    class EntityNotFoundException {
        <<Exception>>
    }
    class SystemExitException {
        <<Exception>>
    }