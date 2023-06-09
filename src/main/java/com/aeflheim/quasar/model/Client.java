package com.aeflheim.quasar.model;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "client_id")
    protected String clientId;

    @Column(name = "secret")
    protected String clientSecret;

    @Column(name = "redirect_uri")
    protected String redirectUri;

    @Column(name = "scope")
    protected String scope;

    @Column(name = "auth_method")
    protected String authenticationMethod;

    @Column(name = "grant_type")
    protected String grantType;

    public Client() {
    }

    public Client(String clientId, String clientSecret, String redirectUri, String scope, String authenticationMethod,
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
                registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue());
        client.setGrantType(registeredClient.getAuthorizationGrantTypes().stream().findAny().get().getValue());

        return client;
    }

    public static RegisteredClient from(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
            .clientId(client.getClientId())
            .clientSecret(client.getClientSecret())
            .scope(client.getScope())
            .redirectUri(client.getRedirectUri())
            .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthenticationMethod()))
            .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(8L))
                        .build())
        .build();
    }
}
