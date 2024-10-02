package co.edu.uniquindio.unieventos.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Compra {

    @Id
    @EqualsAndHashCode.Include
    private String idCompra;

    private Cuenta cuenta;
    private Evento evento;
    private Localidad localidad;
    private Pago pago;
    private int cantidadEntradas;
    private double precioTotal;
    private Date fechaCompra;
    private Cupon cuponAplicado;
}
