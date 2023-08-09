package org.aeros.quasar.config.security.service;

import org.aeros.quasar.core.domain.model.User;
import org.aeros.quasar.core.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class DefaultUserDetailsService implements QsUserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);

		return user.map(SecureUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
	}

	@Override
	public void createUser(String username, String email, String password) {
		User user = new User(username, email, passwordEncoder.encode(password));
		user.setAuthority(Arrays.asList("read", "write", "delete"));

		userRepository.save(user);
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

}
