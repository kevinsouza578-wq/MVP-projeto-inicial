package br.com.mvp.educagames.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 2, max = 120) String name,
        @Size(min = 3, max = 60) String username,
        @Email @Size(max = 160) String email,
        @Size(max = 500) String photoUrl
) {
}
