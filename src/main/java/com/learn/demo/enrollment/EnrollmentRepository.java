package com.learn.demo.enrollment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    // MongoDB-style query method
    boolean existsByUserIdAndCourseId(String userId, String courseId);
}
