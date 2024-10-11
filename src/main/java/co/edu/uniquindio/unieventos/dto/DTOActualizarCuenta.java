package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record DTOActualizarCuenta(

        @NotBlank(message = "La cédula no puede estar vacía")
        @Length(max = 20, message = "La cédula no puede tener más de 10 caracteres")
        String cedula,

        @NotBlank(message="El nombre no puede estar vacío")
        @Length(max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String nombre,

        @Length(max = 100, message = "La dirección no puede tener más de 100 caracteres")
        String direccion,

        @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
        List<@NotBlank(message = "El número de teléfono no puede estar vacío")
        @Length(max = 10, message = "El número de teléfono no puede tener más de 10 caracteres")
        String> telefono,

        @NotBlank(message = "El email no puede estar vacío")
        @Length(max = 50, message = "El email no puede tener más de 50 caracteres")
        @Email(message = "El formato del email no es válido")
        String email
){}
