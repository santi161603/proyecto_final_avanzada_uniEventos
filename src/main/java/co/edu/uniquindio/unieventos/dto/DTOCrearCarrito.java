package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

public record DTOCrearCarrito(
        @NotNull ObjectId usuarioId           // ID del usuario al que pertenece el carrito
) {
}
