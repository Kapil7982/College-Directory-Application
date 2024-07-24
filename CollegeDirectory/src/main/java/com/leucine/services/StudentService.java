package com.leucine.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leucine.exceptions.ResourceNotFoundException;
import com.leucine.models.Department;
import com.leucine.models.StudentProfile;
import com.leucine.models.Users;
import com.leucine.repositories.DepartmentRepository;
import com.leucine.repositories.StudentProfileRepository;
import com.leucine.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {
    @Autowired
    private StudentProfileRepository studentProfileRepository;
    
    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public StudentProfile getStudentProfile(Long userId) {
        return studentProfileRepository.findById(userId).orElse(null);
    }

    public StudentProfile updateStudentProfile(StudentProfile studentProfile) {
        return studentProfileRepository.save(studentProfile);
    }

    public List<StudentProfile> searchStudents(String name, String department, String year) {
        List<StudentProfile> allStudents = studentProfileRepository.findAll();
        
        return allStudents.stream()
            .filter(student -> (name == null || student.getUser().getName().toLowerCase().contains(name.toLowerCase()))
                && (department == null || student.getDepartment().getName().equalsIgnoreCase(department))
                && (year == null || student.getYear().equalsIgnoreCase(year)))
            .collect(Collectors.toList());
    }

    @Transactional
    public StudentProfile createStudentProfile(StudentProfile studentProfile) {
        Users user = usersRepository.findById(studentProfile.getUser().getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        Department department = departmentRepository.findById(studentProfile.getDepartment().getId())
            .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        
        studentProfile.setUser(user);
        studentProfile.setDepartment(department);
        
        return studentProfileRepository.save(studentProfile);
    }
}
