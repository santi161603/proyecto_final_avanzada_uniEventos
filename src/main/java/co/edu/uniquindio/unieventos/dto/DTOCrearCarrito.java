package co.edu.uniquindio.unieventos.dto;

public record DTOCrearCarrito(
        @NotNull ObjectId usuarioId,            // ID del usuario al que pertenece el carrito
) {
}
