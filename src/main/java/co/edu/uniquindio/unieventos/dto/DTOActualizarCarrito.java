package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import org.bson.types.ObjectId;

import java.util.List;

public record DTOActualizarCarrito(
        @NotEmpty List<ObjectId> Eventoitems,         // Lista de IDs actualizados de los productos
        @NotEmpty @DecimalMin("0.0") Double totalPrecio  // Precio total actualizado
) {
}
