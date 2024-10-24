package co.edu.uniquindio.unieventos.modelo.documentos;


import co.edu.uniquindio.unieventos.modelo.vo.Pago;
import co.edu.uniquindio.unieventos.modelo.vo.Transaccion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ordenes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orden {

    @Id
    @EqualsAndHashCode.Include
    private String idOrden;

    private String codigoPasarela;
    private Transaccion transaccion;
    private Pago pago;
}
