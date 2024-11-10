package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import jakarta.validation.constraints.NotNull;

public record TipoEventoDTO(
        @NotNull TipoEvento tipoEvento
) {
}
