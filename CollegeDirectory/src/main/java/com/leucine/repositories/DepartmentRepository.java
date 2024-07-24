package com.leucine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leucine.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
