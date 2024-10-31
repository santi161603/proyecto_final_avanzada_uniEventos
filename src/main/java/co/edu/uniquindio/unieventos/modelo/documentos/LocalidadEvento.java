package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoLocalidad;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collection = "localidades_eventos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LocalidadEvento {

    @Id
    @EqualsAndHashCode.Include
    private String IdLocalidad;

    private String nombreLocalidad;
    private String direccion;
    private Ciudades ciudad;
    private MultipartFile imagen;
    private TipoLocalidad tipoLocalidad;
    private int capacidadMaxima;
    private int capacidadDisponible;

}
