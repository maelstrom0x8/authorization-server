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
package com.aeflheim.bifrost.client.model;

import jakarta.persistence.*;
import java.time.Duration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "cid")
    @GeneratedValue(generator = "clients_seq")
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_id_seq")
    private Integer id;

    @Column(name = "client_id", length = 16, unique = true, nullable = false)
    private String clientId;

    @Column(name = "secret", length = 16, nullable = false)
    private String clientSecret;

    @Column(name = "redirect_uri", length = 100, nullable = false)
    private String redirectUri;

    @Column(name = "scope", length = 40)
    private String scope;

    @Column(name = "auth_method", length = 30, nullable = false)
    private String authenticationMethod;

    @Column(name = "grant_type", length = 10, nullable = false)
    private String grantType;

    public Client() {}

    public Client(
            String clientId,
            String clientSecret,
            String redirectUri,
            String scope,
            String authenticationMethod,
            String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.authenticationMethod = authenticationMethod;
        this.grantType = grantType;
    }

    public Integer getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public static Client from(RegisteredClient registeredClient) {
        Client client = new Client();

        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setRedirectUri(registeredClient.getRedirectUris().stream().findAny().get());
        client.setScope(registeredClient.getScopes().stream().findAny().get());
        client.setAuthenticationMethod(
                registeredClient.getClientAuthenticationMethods().stream()
                        .findAny()
                        .get()
                        .getValue());
        client.setGrantType(
                registeredClient.getAuthorizationGrantTypes().stream().findAny().get().getValue());

        return client;
    }

    public static RegisteredClient from(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .clientAuthenticationMethod(
                        new ClientAuthenticationMethod(client.getAuthenticationMethod()))
                .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
                .tokenSettings(
                        TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .build();
    }
}
