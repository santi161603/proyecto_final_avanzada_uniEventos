package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record DTOActualizarCuenta(

        @NotBlank(message = "La cédula no puede estar vacía")
        @Length(max = 20, message = "La cédula no puede tener más de 10 caracteres")
        String cedula,

        @NotBlank(message="El nombre no puede estar vacío")
        @Length(max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String nombre,

        @NotBlank(message="El apellido no puede estar vacío")
        @Length(max = 100, message = "El apellido no puede tener más de 100 caracteres")
        String apellido,

        @Length(max = 100, message = "La dirección no puede tener más de 100 caracteres")
        String direccion,

        @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
        List<String> telefono,

        @NotNull
        Ciudades ciudad,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato del email no es válido")
        String email
){}
