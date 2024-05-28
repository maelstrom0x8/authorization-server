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
package com.aeflheim.bifrost;

import static org.assertj.core.api.Assertions.assertThat;

import com.aeflheim.bifrost.client.model.Client;
import com.aeflheim.bifrost.client.repository.ClientRepository;
import com.aeflheim.bifrost.user.model.Privilege;
import com.aeflheim.bifrost.user.model.Role;
import com.aeflheim.bifrost.user.model.User;
import com.aeflheim.bifrost.user.repository.RoleRepository;
import com.aeflheim.bifrost.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestContainersConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Autowired private UserRepository userRepository;

    @Autowired private ClientRepository clientRepository;

    @Autowired private EntityManager em;

    @Autowired private RoleRepository roleRepository;

    @Test
    void test() {
        Client client =
                new Client(
                        "client_id",
                        "secret",
                        "https://example.com",
                        "openid",
                        "client_basic_secret",
                        "code");

        Set<Privilege> privilegeSet =
                Stream.of("read", "write").map(Privilege::new).collect(Collectors.toSet());
        for (Privilege privilege : privilegeSet) em.persist(privilege);
        Role roleUser = new Role("ROLE_USER");
        roleUser.setAuthorities(privilegeSet);
        roleRepository.save(roleUser);

        User user = new User("username", "email", "pass");
        user.getRoles().add(roleUser);

        userRepository.save(user);
        clientRepository.save(client);

        List<User> users = userRepository.findAll();
        List<Client> clients = clientRepository.findAll();

        assertThat(users).isNotEmpty();
        assertThat(clients).isNotEmpty();
    }
}
