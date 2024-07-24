package com.leucine.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leucine.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("SELECT c FROM Course c WHERE c.faculty.userId = :facultyId")
    List<Course> findByFacultyId(Long facultyId);
}
