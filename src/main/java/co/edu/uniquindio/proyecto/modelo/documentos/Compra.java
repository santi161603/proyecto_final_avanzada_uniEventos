package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.Localidad;
import co.edu.uniquindio.proyecto.modelo.vo.Pago;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Compra {

    @Id
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
