package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;

public interface CuentaServicio {

    default void crearCuenta(DtoCuenta cuenta){}

}
