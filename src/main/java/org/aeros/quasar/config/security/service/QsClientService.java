package org.aeros.quasar.config.security.service;

import org.aeros.quasar.core.domain.model.Client;
import org.aeros.quasar.core.domain.repository.ClientRepository;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QsClientService implements RegisteredClientRepository {

    private ClientRepository clientRepository;

    public QsClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        clientRepository.save(Client.from(registeredClient));
    }

    @Override
    @Nullable
    public RegisteredClient findById(String id) {
        var client = clientRepository.findById(Integer.valueOf(id))
                .orElseThrow();

        return Client.from(client);
    }

    @Override
    @Nullable
    public RegisteredClient findByClientId(String clientId) {
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow();

        return Client.from(client);
    }

}
