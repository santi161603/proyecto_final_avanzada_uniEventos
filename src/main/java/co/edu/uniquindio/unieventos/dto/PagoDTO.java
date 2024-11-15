package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PagoDTO(

        @NotBlank(message = "El método de pago no puede estar en blanco")
        String metodoPago // MercadoPago, PayPal, etc.

) {
}
