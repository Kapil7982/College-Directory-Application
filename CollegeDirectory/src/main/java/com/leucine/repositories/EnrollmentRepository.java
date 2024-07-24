package com.leucine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leucine.models.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
