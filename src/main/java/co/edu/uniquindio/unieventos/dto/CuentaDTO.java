package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CuentaDTO(
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 100) String apellido,
        @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
        List<@Valid @NotBlank(message = "El número de teléfono no puede estar en blanco") @Length(max = 15) String> telefono,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email
) {
}
