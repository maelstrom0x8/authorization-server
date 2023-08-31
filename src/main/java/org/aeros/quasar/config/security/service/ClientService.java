package org.aeros.quasar.config.security.service;

import org.aeros.quasar.web.dto.ClientRegisterDto;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface ClientService extends RegisteredClientRepository {

    String save(ClientRegisterDto client);
}
