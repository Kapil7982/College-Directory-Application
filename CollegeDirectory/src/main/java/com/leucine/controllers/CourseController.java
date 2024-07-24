package com.leucine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.leucine.models.Course;
import com.leucine.models.Department;
import com.leucine.models.FacultyProfile;
import com.leucine.services.CourseService;
import com.leucine.services.DepartmentService;
import com.leucine.services.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FacultyService facultyProfileService; // Add this service
    @Autowired
    private DepartmentService departmentService; // Add this service

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        // Fetch the FacultyProfile and Department from DB and set them to the course
        FacultyProfile faculty = facultyProfileService.getFacultyProfileById(course.getFaculty().getUserId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        Department department = departmentService.getDepartmentById(course.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        course.setFaculty(faculty);
        course.setDepartment(department);

        return ResponseEntity.ok(courseService.createCourse(course));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
