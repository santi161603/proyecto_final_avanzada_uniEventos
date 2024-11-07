package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CuponObtenidoDTO(
        @NotBlank(message = "El nombre del cupón no puede estar en blanco")
        String nombreCupon,

        @NotBlank(message = "La descripción del cupón no puede estar en blanco")
        String descripcionCupon,

        @DecimalMin(value = "0.0", inclusive = false, message = "El porcentaje de descuento debe ser mayor que 0")
        @DecimalMax(value = "80.0", message = "El porcentaje de descuento no puede ser mayor que 80")
        double porcentajeDescuento,

        @NotNull(message = "La fecha de vencimiento no puede ser nula")
        @Future(message = "La fecha de vencimiento debe estar en el futuro")
        LocalDateTime fechaVencimiento
) {
}