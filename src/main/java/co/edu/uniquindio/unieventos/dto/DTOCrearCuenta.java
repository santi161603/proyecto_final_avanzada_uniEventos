package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record DTOCrearCuenta(
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 10) List<String> telefono,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String contrasena,
        @NotBlank RolUsuario rol  // O puedes cambiarlo por un tipo específico de RolUsuario
) {
}
