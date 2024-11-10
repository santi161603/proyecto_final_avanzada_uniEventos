package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NombreyIdLocalidadObtenidaDTO(

        @NotBlank(message = "El nombre de la localidad no puede estar en blanco")
        @Size(min = 3, max = 100, message = "El nombre de la localidad debe tener entre 3 y 100 caracteres")
        String nombreLocalidad,

        @NotBlank(message = "El ID de la localidad no puede estar en blanco")
        String IdLocalidad

) {
}
