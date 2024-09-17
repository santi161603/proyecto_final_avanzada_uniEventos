package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.Localidad;
import co.edu.uniquindio.proyecto.modelo.vo.Pago;

import java.util.Date;

public class Compra {
    private String idCompra;
    private Cliente cliente;
    private Evento evento;
    private Localidad localidad;
    private Pago pago;
    private int cantidadEntradas;
    private double precioTotal;
    private Date fechaCompra;
    private Cupon cuponAplicado;
}
