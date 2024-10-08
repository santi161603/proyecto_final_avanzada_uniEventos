package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.TipoLocalidad;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
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
    private TipoLocalidad tipoLocalidad;
    private int capacidadMaxima;
    private int capacidadDisponible;

}