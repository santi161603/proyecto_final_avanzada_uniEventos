package co.edu.uniquindio.unieventos.modelo.documentos;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "transacciones")
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
    private ObjectId idPago;
    private String qr;
}
