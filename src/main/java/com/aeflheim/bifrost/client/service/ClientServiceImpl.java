/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aeflheim.bifrost.client.service;

import com.aeflheim.bifrost.client.model.Client;
import com.aeflheim.bifrost.client.repository.ClientRepository;
import com.aeflheim.bifrost.client.web.dto.ClientRegisterDto;
import com.aeflheim.bifrost.config.security.jose.KeyGeneratorUtils;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired private ClientRepository clientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        clientRepository.save(Client.from(registeredClient));
    }

    @Override
    @Nullable public RegisteredClient findById(String id) {
        var client = clientRepository.findById(Integer.valueOf(id)).orElseThrow();

        return Client.from(client);
    }

    @Override
    @Nullable public RegisteredClient findByClientId(String clientId) {
        var client = clientRepository.findByClientId(clientId).orElseThrow();

        return Client.from(client);
    }

    @Override
    public String save(ClientRegisterDto client) {
        String secret = generateClientSecret();
        RegisteredClient registeredClient =
                RegisteredClient.withId(client.clientId())
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
        return KeyGeneratorUtils.generateSecret(8);
    }

    private Consumer<Set<String>> defaultClientScopes() {
        return set -> {
            set.add("openid");
            set.add("read");
            set.add("write");
        };
    }
}
