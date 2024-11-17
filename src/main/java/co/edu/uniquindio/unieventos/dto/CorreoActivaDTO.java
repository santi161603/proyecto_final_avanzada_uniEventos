package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CorreoActivaDTO(
        @NotBlank
        @Length(max = 50) @Email
        String correo,
        CodigoVerificacionDTO codigo
) {
}
