package main.java.com.airtribe.learntrack.entity;

import java.util.Date;

public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private Date enrollmentDate;
    private STATUSENUM status;

    public Enrollment(int id, int studentId, int courseId, Date enrollmentDate, STATUSENUM status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public STATUSENUM getStatus() {
        return status;
    }

    public void setStatus(STATUSENUM stats) {
        this.status = stats;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", enrollmentDate=" + enrollmentDate +
                ", status=" + status;
    }
}
