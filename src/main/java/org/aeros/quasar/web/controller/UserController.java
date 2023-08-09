package org.aeros.quasar.web.controller;

import org.aeros.quasar.core.domain.dto.UserDto;
import org.aeros.quasar.core.service.UserService;
import org.aeros.quasar.core.domain.dto.NewPasswordDto;
import org.aeros.quasar.core.domain.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestParam RegisterDto registerDto) {
        return ResponseEntity.of(userService.createUser(registerDto));
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestParam NewPasswordDto newPasswordDto) {
        userService.changePassword(newPasswordDto);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
    }
}
