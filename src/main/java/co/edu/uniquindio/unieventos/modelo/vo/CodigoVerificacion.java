package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CodigoVerificacion {

    private int codigo;
    private LocalDateTime fecha;

}
