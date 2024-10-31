package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RestablecerContrasenaDTO(
        @NotBlank @Length(min = 7, max = 20) String contrasenaNueva,
        @NotBlank @Length(max = 50) @Email String email
) {
}
