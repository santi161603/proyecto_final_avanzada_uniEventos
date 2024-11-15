package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
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
    private Ciudades ciudad;
    private TipoEvento tipoEvento;
    private double porcentajeDescuento;
    private LocalDate fechaVencimiento;
    private int cantidad;
}
