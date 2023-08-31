package org.aeros.quasar.web.controller;

import org.aeros.quasar.config.security.service.ClientService;
import org.aeros.quasar.web.dto.ClientRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public void registerClient(ClientRegisterDto client) {
        RegisteredClient registeredClient = RegisteredClient.withId(client.clientId())
                .redirectUri(client.redirectUri())
                .build();

        clientService.save(registeredClient);
    }

}
