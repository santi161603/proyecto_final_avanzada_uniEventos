package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubEvento {
    private LocalDateTime fechaEvento;
    private LocalTime horaEvento;
    private String localidad;
    private int cantidadEntradas;
    private float precioEntrada;
}
