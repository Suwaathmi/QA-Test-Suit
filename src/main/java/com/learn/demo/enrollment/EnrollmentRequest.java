package com.learn.demo.enrollment;

import jakarta.validation.constraints.NotNull;

public class EnrollmentRequest {

    @NotNull
    private String userId;

    @NotNull
    private String courseId;

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
}
