package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record DTOEventoCarrito(
        @NotBlank String idCarrito,            // ID del carrito relacionado con el evento
        @NotBlank String tipoEvento,           // Tipo de evento: "CREAR", "ACTUALIZAR", "ELIMINAR"
        @NotNull LocalDateTime fechaEvento           // Fecha y hora del evento
) {
}
