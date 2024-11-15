package co.edu.uniquindio.unieventos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PagoObtenidoDTO(

         String metodoPago, // MercadoPago, PayPal, etc.
         String estadoPago, // completado, pendiente, fallido
         LocalDate fechaPago,
         String detalleEstadoPago,
         String tipoPago,
         String moneda
) { }
