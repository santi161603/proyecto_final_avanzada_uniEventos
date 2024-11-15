package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemCarritoVO {

    private String eventoId;
    private int idSubevento;
    private int cantidadEntradas;
    private String cupon;
}
