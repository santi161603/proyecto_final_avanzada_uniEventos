package co.edu.uniquindio.proyecto.modelo.documentos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Cupon {
    @Id
    private String codigoCupon;

    private String nombreCupon;
    private double porcentajeDescuento;
    private Date fechaVencimiento;
}
