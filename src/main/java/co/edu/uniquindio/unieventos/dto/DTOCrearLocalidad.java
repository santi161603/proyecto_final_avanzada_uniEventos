package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoLocalidad;
import jakarta.validation.constraints.*;

public record DTOCrearLocalidad(

        @NotBlank(message = "El nombre de la localidad no puede estar en blanco")
        @Size(min = 3, max = 100, message = "El nombre de la localidad debe tener entre 3 y 100 caracteres")
        String nombreLocalidad,

        @NotBlank(message = "La dirección no puede estar en blanco")
        @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
        String direccion,

        @NotNull(message = "La ciudad no puede ser nula")
        Ciudades ciudad,

        @NotNull(message = "El tipo de localidad no puede ser nulo")
        TipoLocalidad tipoLocalidad,

        @Positive(message = "La capacidad máxima debe ser un número positivo")
        int capacidadMaxima,

        @PositiveOrZero(message = "La capacidad disponible no puede ser negativa")
        int capacidadDisponible) {

}
