package co.edu.uniquindio.unieventos.dto;

public record DTOCrearCarrito(
        @NotNull ObjectId usuarioId,            // ID del usuario al que pertenece el carrito
        @NotEmpty List<ObjectId> items,         // Lista de IDs de los productos en el carrito
        @NotNull @DecimalMin("0.0") Double totalPrecio  // Precio total del carrito
) {
}
