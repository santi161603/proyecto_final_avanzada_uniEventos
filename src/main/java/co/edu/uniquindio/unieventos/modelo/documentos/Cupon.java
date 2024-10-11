package co.edu.uniquindio.unieventos.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cupones")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cupon {
    @Id
    @EqualsAndHashCode.Include
    private String codigoCupon;

    private String nombreCupon;
    private double porcentajeDescuento;
    private Date fechaVencimiento;
    private int cantidad;
}
