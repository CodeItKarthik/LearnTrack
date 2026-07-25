package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.enums.STATUSENUM;

import java.time.LocalDate;

public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;
    private STATUSENUM status;

    public Enrollment(int id, int studentId, int courseId, LocalDate enrollmentDate, STATUSENUM status) {
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

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
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
