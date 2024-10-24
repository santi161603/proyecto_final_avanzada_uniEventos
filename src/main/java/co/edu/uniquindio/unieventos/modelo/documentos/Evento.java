package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "eventos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {

    @Id
    @EqualsAndHashCode.Include
    private String idEvento;

    private String nombre;
    private Ciudades ciudad;
    private String descripcion;
    private TipoEvento tipoEvento; // concierto, teatro, deporte, etc.
    private String imagenPoster;
    private List<SubEvento> subEvent;
}
