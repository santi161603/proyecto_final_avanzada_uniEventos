package co.edu.uniquindio.proyecto.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reporte{

    private String tipoReporte;
    private Date fechaGeneracion;
    private String contenido;

}
