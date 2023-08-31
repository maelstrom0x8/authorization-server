package org.aeros.quasar.web.controller;

import org.aeros.quasar.config.security.service.UserService;
import org.aeros.quasar.core.domain.dto.NewPasswordDto;
import org.aeros.quasar.core.domain.dto.RegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto user) {
        String email = userService.createUser(user.username(), user.email(), user.password());
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestParam NewPasswordDto request) {
        userService.updatePassword(request.oldPassword(), request.newPassword());
    }
}
