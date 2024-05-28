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
package com.aeflheim.bifrost.config.security.service;

import com.aeflheim.bifrost.user.model.User;
import com.aeflheim.bifrost.user.repository.RoleRepository;
import com.aeflheim.bifrost.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements SecurityService {

    @Autowired private UserRepository userRepository;

    @Autowired private RoleRepository roleRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(SecureUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    @Override
    public String createUser(String username, String email, String password) {
        User user = new User(username, email, passwordEncoder.encode(password));
        return userRepository.save(user).getEmail();
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.map(SecureUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        userRepository.updatePassword(user(), oldPassword, newPassword);
    }

    private String user() {
        return null;
    }
}
