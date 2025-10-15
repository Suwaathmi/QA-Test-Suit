package com.learn.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/courses")
public class CourseRestController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/getCourseById/{course_id}")
    public ResponseEntity<?> getCourseById(@PathVariable("course_id") String courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestBody Course newCourse) {
        courseRepository.save(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Course " + newCourse.getCategory() + " " + newCourse.getDescription() + " is added");
    }

    @PutMapping("/updateCourse/{instructor_Id}/{course_Id}")
    public ResponseEntity<String> updateCourse(
            @PathVariable String instructor_Id,
            @PathVariable String course_Id,
            @RequestBody Course updateCourse) {

        Optional<Course> optionalCourse = courseRepository.findById(course_Id);
        if (optionalCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID " + course_Id + " not found");
        }

        Course existingCourse = optionalCourse.get();
        if (!existingCourse.getInstructor_Id().equals(instructor_Id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Instructor " + instructor_Id + " is not authorized to update this course");
        }

        existingCourse.setDescription(updateCourse.getDescription());
        existingCourse.setCategory(updateCourse.getCategory());
        existingCourse.setPrice(updateCourse.getPrice());
        existingCourse.setImage_url(updateCourse.getImage_url());

        courseRepository.save(existingCourse);

        return ResponseEntity.ok(
                "Course '" + existingCourse.getDescription() + "' updated successfully by instructor " + instructor_Id
        );
    }

    @DeleteMapping("/deleteCourse/{instructor_Id}/{course_Id}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable String instructor_Id,
            @PathVariable String course_Id) {

        Optional<Course> optionalCourse = courseRepository.findById(course_Id);
        if (optionalCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID " + course_Id + " not found");
        }

        Course courseToDelete = optionalCourse.get();
        if (!courseToDelete.getInstructor_Id().equals(instructor_Id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Instructor " + instructor_Id + " is not authorized to delete this course");
        }

        courseRepository.deleteById(course_Id);

        return ResponseEntity.ok(
                "Course '" + courseToDelete.getDescription() + "' deleted successfully by instructor " + instructor_Id
        );
    }
}
