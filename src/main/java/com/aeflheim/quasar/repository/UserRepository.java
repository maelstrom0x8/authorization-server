package com.aeflheim.quasar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aeflheim.quasar.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	void deleteByUsername(String username);

}
