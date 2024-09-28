package co.edu.uniquindio.proyecto.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Localidad {

    @Id
    @EqualsAndHashCode.Include
    private String IdLocalidad;

    private String nombreLocalidad;
    private double precio;
    private int capacidadMaxima;
    private int capacidadDisponible;

}
