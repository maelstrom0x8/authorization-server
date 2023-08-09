package org.aeros.quasar.core.domain.dto;

public record NewPasswordDto(String email, String oldPassword, String newPassword) {
}
