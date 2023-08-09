package org.aeros.quasar.core.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.aeros.quasar.core.domain.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByClientId(String clientId);
}
