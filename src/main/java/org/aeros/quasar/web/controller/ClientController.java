package org.aeros.quasar.web.controller;

import org.aeros.quasar.config.security.service.ClientService;
import org.aeros.quasar.web.dto.ClientRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public String registerClient(ClientRegisterDto client) {
        return clientService.save(client);
    }

}
