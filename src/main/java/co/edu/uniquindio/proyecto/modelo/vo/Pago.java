package co.edu.uniquindio.proyecto.modelo.vo;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoPago;

import java.util.Date;

public class Pago {
    private String idPago;
    private String metodoPago; // MercadoPago, PayPal, etc.
    private EstadoPago estadoPago; // completado, pendiente, fallido
    private Date fechaPago;
    private double montoTotal;

}
