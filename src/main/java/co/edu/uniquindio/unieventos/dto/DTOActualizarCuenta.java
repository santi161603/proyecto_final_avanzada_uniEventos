package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record DTOActualizarCuenta(
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length(max = 100) String nombre,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 10) List<String> telefono,
        @NotBlank @Length(max = 50) @Email String email
) {
}
