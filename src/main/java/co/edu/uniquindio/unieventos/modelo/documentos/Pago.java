package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoPago;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago {

    @Id
    @EqualsAndHashCode.Include
    private String idPago;

    private String metodoPago; // MercadoPago, PayPal, etc.
    private EstadoPago estadoPago; // completado, pendiente, fallido
    private Date fechaPago;
    private double montoTotal;

}