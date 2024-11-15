package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoPago;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pago {

    private String pagoCodigo;
    private String metodoPago; // MercadoPago, PayPal, etc.
    private String estadoPago; // completado, pendiente, fallido
    private LocalDate fechaPago;
    private String detalleEstadoPago;
    private String tipoPago;
    private String moneda;
    private String codifoAutorizacion;
}
