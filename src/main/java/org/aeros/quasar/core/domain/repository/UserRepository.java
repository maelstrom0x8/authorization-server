package org.aeros.quasar.core.domain.repository;

import org.aeros.quasar.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Query("UPDATE User u SET password = :newPass WHERE u.username = :username AND u.password = :password")
    void updatePassword(@Param("username") String user,
                        @Param("password") String oldPassword,
                        @Param("newPass") String newPassword);
}
