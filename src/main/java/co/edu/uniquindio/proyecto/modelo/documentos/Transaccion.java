package co.edu.uniquindio.proyecto.modelo.documentos;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document()
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaccion {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    private List<Evento> productos;
    private ObjectId idCliente;
    private LocalDateTime fecha;
    private ObjectId idPago;
}
