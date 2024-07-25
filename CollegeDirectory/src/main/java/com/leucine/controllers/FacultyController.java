package com.leucine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.leucine.models.Course;
import com.leucine.models.FacultyProfile;
import com.leucine.services.FacultyService;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'FACULTY_MEMBER')")
    public ResponseEntity<FacultyProfile> getFacultyProfile(@PathVariable Long userId) {
        FacultyProfile profile = facultyService.getFacultyProfile(userId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<FacultyProfile> createFacultyProfile(@RequestBody FacultyProfile facultyProfile) {
        FacultyProfile createProfile = facultyService.createFacultyProfile(facultyProfile);
        return ResponseEntity.ok(createProfile);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'FACULTY_MEMBER')")
    public ResponseEntity<FacultyProfile> updateFacultyProfile(@RequestBody FacultyProfile facultyProfile) {
        FacultyProfile updatedProfile = facultyService.updateFacultyProfile(facultyProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{facultyId}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'FACULTY_MEMBER')")
    public ResponseEntity<List<Course>> getFacultyClasses(@PathVariable Long facultyId) {
        List<Course> classes = facultyService.getFacultyClasses(facultyId);
        return ResponseEntity.ok(classes);
    }
}
