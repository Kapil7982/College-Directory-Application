package com.leucine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.leucine.exceptions.ResourceNotFoundException;
import com.leucine.models.StudentProfile;
import com.leucine.services.StudentService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasAnyAuthority('STUDENT',ADMINISTRATOR)")
    public ResponseEntity<StudentProfile> getStudentProfile(@PathVariable Long userId) {
        StudentProfile profile = studentService.getStudentProfile(userId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'STUDENT')")
    public ResponseEntity<StudentProfile> updateStudentProfile(@RequestBody StudentProfile studentProfile) {
        StudentProfile updatedProfile = studentService.updateStudentProfile(studentProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'FACULTY_MEMBER')")
    public ResponseEntity<List<StudentProfile>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String year) {
        List<StudentProfile> students = studentService.searchStudents(name, department, year);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<StudentProfile> createStudentProfile(@RequestBody StudentProfile studentProfile) {
        try {
            StudentProfile createdProfile = studentService.createStudentProfile(studentProfile);
            return ResponseEntity.ok(createdProfile);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
