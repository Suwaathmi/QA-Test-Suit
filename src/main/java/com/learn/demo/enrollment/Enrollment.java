package com.learn.demo.enrollment;

import com.learn.demo.course.Course;
import com.learn.demo.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "enrollment")
public class Enrollment {

    @Id
    private String id; // MongoDB uses String IDs

    private String userId;    // Reference to User document
    private String courseId;  // Reference to Course document

    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @CreatedDate
    private LocalDateTime enrolled_At;

    public Enrollment() {}

    public Enrollment(String userId, String courseId, EnrollmentStatus status, LocalDateTime enrolled_At) {
        this.userId = userId;
        this.courseId = courseId;
        this.status = status;
        this.enrolled_At = enrolled_At;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getEnrolled_At() {
        return enrolled_At;
    }

    public void setEnrolled_At(LocalDateTime enrolled_At) {
        this.enrolled_At = enrolled_At;
    }
}
