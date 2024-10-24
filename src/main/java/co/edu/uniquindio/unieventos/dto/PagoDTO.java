package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PagoDTO(

        @NotBlank(message = "El método de pago no puede estar en blanco")
        String metodoPago, // MercadoPago, PayPal, etc.

        @NotNull(message = "El estado de pago no puede ser nulo")
        EstadoPago estadoPago, // completado, pendiente, fallido

        @NotNull(message = "La fecha de pago no puede ser nula")
        LocalDateTime fechaPago,

     // hay que implementar el cupon para usar esto
     /*   @NotBlank(message = "El cupón no puede estar en blanco")
        String cupon,*/

        @Positive(message = "El monto total debe ser un número positivo")
        double montoTotal

) {
}
