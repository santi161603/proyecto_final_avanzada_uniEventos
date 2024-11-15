package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubEvento {
    private  int idSubEvento;
    private LocalDate fechaEvento;
    private String horaEvento;
    private String localidad;
    private EstadoCuenta estadoSubevento;
    private int cantidadEntradas;
    private double precioEntrada;
}
