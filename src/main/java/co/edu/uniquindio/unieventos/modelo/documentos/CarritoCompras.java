package co.edu.uniquindio.unieventos.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarritoCompras {

    @Id
    @EqualsAndHashCode.Include
    private String idCarritoCompras;      // Identificador único del carrito

    private Long usuarioId;       // Identificador del usuario dueño del carrito
    private List<Evento> items;         // Lista de ítems (entradas) en el carrito
    private Double totalPrecio;              // Precio total de los ítems en el carrito
    private String estado;
}
