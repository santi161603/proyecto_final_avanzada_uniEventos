package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.Localidad;

import java.util.Date;

public class Compra {
    private String idCompra;
    private Cliente cliente;
    private Evento evento;
    private Localidad localidad;
    private int cantidadEntradas;
    private double precioTotal;
    private String codigoQR;
    private Date fechaCompra;
    private Cupon cuponAplicado;
}
