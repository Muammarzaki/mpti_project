package uin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(@NotEmpty String username,
                          @NotEmpty String password,
                          @Email String email) {
}
