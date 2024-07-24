package com.leucine.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leucine.models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	public Optional<Users> findByEmail(String email);
}
