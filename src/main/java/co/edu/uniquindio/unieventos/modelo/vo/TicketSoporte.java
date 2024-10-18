package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketSoporte {

    private String descripcionProblema;
    private Date fechaCreacion;
    private String estado;
    private String usuario;

}
