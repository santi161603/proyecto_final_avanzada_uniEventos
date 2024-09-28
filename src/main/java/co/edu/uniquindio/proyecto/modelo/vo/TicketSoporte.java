package co.edu.uniquindio.proyecto.modelo.vo;

import co.edu.uniquindio.proyecto.modelo.documentos.Cuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketSoporte {

    private String descripcionProblema;
    private Date fechaCreacion;
    private String estado;
    private Cuenta cuenta;

}
