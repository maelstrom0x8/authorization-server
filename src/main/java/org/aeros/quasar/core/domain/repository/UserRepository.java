package org.aeros.quasar.core.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.aeros.quasar.core.domain.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	void deleteByUsername(String username);

}
