package org.aeros.quasar.core.service;

import org.aeros.quasar.core.domain.dto.NewPasswordDto;
import org.aeros.quasar.core.domain.dto.RegisterDto;
import org.aeros.quasar.core.domain.dto.UserDto;
import org.aeros.quasar.core.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDto getByEmail(String email) {
        return null;
    }

    public Optional<UserDto> createUser(RegisterDto registerDto) {
        return Optional.empty();
    }

    public void changePassword(NewPasswordDto newPasswordDto) {
    }

    public void forgotPassword(String email) {
    }
}
