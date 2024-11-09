package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CodigoVerificacionDTO(
        @NotNull(message = "El código no puede ser nulo")
         int codigo
) {
}
