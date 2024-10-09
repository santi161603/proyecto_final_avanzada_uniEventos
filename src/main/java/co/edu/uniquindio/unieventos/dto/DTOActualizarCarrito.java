package co.edu.uniquindio.unieventos.dto;

public record DTOActualizarCarrito(
        @NotEmpty List<ObjectId> items,         // Lista de IDs actualizados de los productos
        @NotNull @DecimalMin("0.0") Double totalPrecio  // Precio total actualizado
) {
}
