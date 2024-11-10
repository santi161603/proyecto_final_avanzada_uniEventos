package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCupon;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String descripcionCupon;
    private EstadoCupon estadoCupon;
    private String userCupon;
    private double porcentajeDescuento;
    private LocalDate fechaVencimiento;
    private int cantidad;
}
