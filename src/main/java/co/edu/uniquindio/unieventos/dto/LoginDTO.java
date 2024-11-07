package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank @Length(min = 7, max = 20) String contrasena,
        @NotBlank @Length(max = 50) @Email String email
) {}
