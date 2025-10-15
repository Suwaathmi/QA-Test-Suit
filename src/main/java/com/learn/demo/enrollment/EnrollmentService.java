package com.learn.demo.enrollment;

import com.learn.demo.course.CourseRepository;
import com.learn.demo.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    public EnrollmentService(EnrollmentRepository e, UserRepository u, CourseRepository c) {
        this.enrollmentRepo = e;
        this.userRepo = u;
        this.courseRepo = c;
    }

    public Enrollment enrollByIds(String userId, String courseId) {

        // Validate user existence
        if (!userRepo.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Validate course existence
        if (!courseRepo.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        // Check for duplicate enrollment
        if (enrollmentRepo.existsByUserIdAndCourseId(userId, courseId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already enrolled");
        }

        // Create and save enrollment
        Enrollment e = new Enrollment();
        e.setUserId(userId);
        e.setCourseId(courseId);
        e.setStatus(EnrollmentStatus.ACTIVE);

        return enrollmentRepo.save(e);
    }
}
