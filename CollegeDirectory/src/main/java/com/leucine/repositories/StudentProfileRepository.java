package com.leucine.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.leucine.models.StudentProfile;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
}
