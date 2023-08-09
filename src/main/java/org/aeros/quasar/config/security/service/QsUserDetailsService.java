package org.aeros.quasar.config.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface QsUserDetailsService extends UserDetailsService {

	void createUser(String username, String email, String password);

	UserDetails loadUserByEmail(String email);

	void deleteUser(String username);

}
