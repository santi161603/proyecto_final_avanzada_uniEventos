package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CuentaObtenidaClienteDTO(
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 100) String apellido,
        @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
        List<@Valid @NotBlank(message = "El número de teléfono no puede estar en blanco") @Length(max = 10) String> telefono,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,
        @NotNull Ciudades ciudad,
        @NotBlank String imageProfile
) {
}
