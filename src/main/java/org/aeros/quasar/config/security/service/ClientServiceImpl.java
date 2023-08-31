package org.aeros.quasar.config.security.service;

import org.aeros.quasar.core.domain.model.Client;
import org.aeros.quasar.core.domain.repository.ClientRepository;
import org.aeros.quasar.web.dto.ClientRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.function.Consumer;

import static org.aeros.quasar.config.security.jose.KeyGeneratorUtils.generateSecret;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

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

    @Override
    public String save(ClientRegisterDto client) {
        String secret = generateClientSecret();
        RegisteredClient registeredClient = RegisteredClient.withId(client.clientId())
                .redirectUri(client.redirectUri())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientSecret(secret)
                .scopes(defaultClientScopes())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .build();
        save(registeredClient);
        return secret;
    }

    private String generateClientSecret() {
        return generateSecret(8);
    }

    private Consumer<Set<String>> defaultClientScopes() {
        return  set -> {
            set.add("openid");
            set.add("read");
            set.add("write");
        };
    }
}
