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
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<FacultyProfile> getFacultyProfile(@PathVariable Long userId) {
        FacultyProfile profile = facultyService.getFacultyProfile(userId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<FacultyProfile> createFacultyProfile(@RequestBody FacultyProfile facultyProfile) {
        FacultyProfile createProfile = facultyService.createFacultyProfile(facultyProfile);
        return ResponseEntity.ok(createProfile);
    }

    @PutMapping("/profile")
    public ResponseEntity<FacultyProfile> updateFacultyProfile(@RequestBody FacultyProfile facultyProfile) {
        FacultyProfile updatedProfile = facultyService.updateFacultyProfile(facultyProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<List<Course>> getFacultyClasses(@PathVariable Long facultyId) {
        List<Course> classes = facultyService.getFacultyClasses(facultyId);
        return ResponseEntity.ok(classes);
    }
}
