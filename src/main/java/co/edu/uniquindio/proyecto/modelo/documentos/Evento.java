package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.enums.TipoEvento;
import co.edu.uniquindio.proyecto.modelo.vo.Localidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Evento {

    @Id
    private String idEvento;

    private String nombre;
    private String direccion;
    private String ciudad;
    private String descripcion;
    private TipoEvento tipoEvento; // concierto, teatro, deporte, etc.
    private String imagenPoster;
    private String imagenLocalidades;
    private Date fechaEvento;
    private List<Localidad> localidades;
}
