package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import jakarta.validation.constraints.NotNull;

public record CiudadesDTO(
        @NotNull(message = "La ciudad no puede ser nula")
        Ciudades ciudad
) {
}
