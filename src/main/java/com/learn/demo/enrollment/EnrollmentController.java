package com.learn.demo.enrollment;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping("/doEnrollment")
    public ResponseEntity<String> doEnrollment(@RequestBody EnrollmentRequest body) {
        Enrollment saved = service.enrollByIds(body.getUserId(), body.getCourseId());

        String id = saved.getId(); // MongoDB uses String IDs
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Enrollment " + id + " created");
    }
}
