package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoPago;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pago {

    private String metodoPago; // MercadoPago, PayPal, etc.
    private EstadoPago estadoPago; // completado, pendiente, fallido
    private LocalDateTime fechaPago;
    private String cupon;
    private double montoTotal;
}
