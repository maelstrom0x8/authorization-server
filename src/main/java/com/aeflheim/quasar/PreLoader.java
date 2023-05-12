package com.aeflheim.quasar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aeflheim.quasar.model.Client;
import com.aeflheim.quasar.service.QsClientService;
import com.aeflheim.quasar.service.userdetails.QsUserDetailsService;

@Component
public class PreLoader implements CommandLineRunner {

    @Autowired
    QsClientService clientService;

    @Autowired
    private QsUserDetailsService userDetailsService;

    @Value("${quasar.client.express.client-id}")
    private String expressClientID;
    @Value("${quasar.client.express.secret}")
    private String expressClientSecret;
    @Value("${quasar.client.express.redirect-uri}")
    private String expressRedirectUri;

    @Override
    public void run(String... args) throws Exception {

        userDetailsService.createUser("anna", "anna32@quasar.org", "sandstorm");
        userDetailsService.createUser("bob", "bobby@quasar.org", "12345");

        Client client = new Client();
        client.setClientId("client");
        client.setClientSecret("secret");
        client.setRedirectUri("http://localhost:3000/authorized");
        client.setScope("openid");
        client.setAuthenticationMethod("client_secret_basic");
        client.setGrantType("authorization_code");

        clientService.save(Client.from(client));

    }
}
