package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class subEvento {
    private LocalDateTime fechaEvento;
    private String localidades;
    private int cantidadEntradas;
}
