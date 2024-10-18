package co.edu.uniquindio.unieventos.modelo.documentos;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document (collection = "carritos_compras")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarritoCompras {

    @Id
    @EqualsAndHashCode.Include
    private String idCarritoCompras;      // Identificador único del carrito

    private String usuarioId;
    private List<String> items;
    private Double totalPrecio;
}
