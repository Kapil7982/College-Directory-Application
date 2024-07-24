package com.leucine.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leucine.models.Course;
import com.leucine.models.FacultyProfile;
import com.leucine.models.Users;
import com.leucine.repositories.CourseRepository;
import com.leucine.repositories.FacultyProfileRepository;
import com.leucine.repositories.UserRepository;


@Service
public class FacultyService {

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository usersRepository;

    public FacultyProfile getFacultyProfile(Long userId) {
        return facultyProfileRepository.findById(userId).orElse(null);
    }

    public FacultyProfile createFacultyProfile(FacultyProfile facultyProfile) {
        // Ensure the user exists and is set in the faculty profile
        Users user = usersRepository.findById(facultyProfile.getUser().getId())
                          .orElseThrow(() -> new RuntimeException("User not found"));
        facultyProfile.setUser(user);
        return facultyProfileRepository.save(facultyProfile);
    }

    public FacultyProfile updateFacultyProfile(FacultyProfile facultyProfile) {
        return facultyProfileRepository.save(facultyProfile);
    }

    public List<Course> getFacultyClasses(Long facultyId) {
        return courseRepository.findByFacultyId(facultyId);
    }
    
    public Optional<FacultyProfile> getFacultyProfileById(Long id) {
        return facultyProfileRepository.findById(id);
    }
}
