package com.aeflheim.quasar;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aeflheim.quasar.model.Client;
import com.aeflheim.quasar.model.User;
import com.aeflheim.quasar.repository.UserRepository;
import com.aeflheim.quasar.service.QsClientService;

@Component
public class PreLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final QsClientService clientService;

    @Value("${quasar.client.express.client-id}")
    private String expressClientID;
    @Value("${quasar.client.express.secret}")
    private String expressClientSecret;
    @Value("${quasar.client.express.redirect-uri}")
    private String expressRedirectUri;


    public PreLoader(UserRepository userRepository, PasswordEncoder passwordEncoder,
            QsClientService clientService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) throws Exception {

        User anna = new User();
        anna.setUsername("anna");
        anna.setPassword(passwordEncoder.encode("sandstorm"));
        anna.setAuthority("read");

        User king = new User();
        king.setUsername("king");
        king.setPassword(passwordEncoder.encode("infinity"));
        king.setAuthority("openid");

        userRepository.saveAll(List.of(anna, king));

        Client client = new Client();
        client.setClientId(expressClientID);
        client.setClientSecret(expressClientSecret);
        client.setRedirectUri(expressRedirectUri);
        client.setScope("openid");
        client.setAuthenticationMethod("client_secret_basic");
        client.setGrantType("authorization_code");
        
        clientService.save(Client.from(client));



        
    }
}
