package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Notificacion {
    private String mensaje;
    private Date fechaEnvio;
    private boolean leida;
}
