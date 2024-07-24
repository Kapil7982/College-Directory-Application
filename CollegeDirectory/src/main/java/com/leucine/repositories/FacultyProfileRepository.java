package com.leucine.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.leucine.models.FacultyProfile;

public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {
}
