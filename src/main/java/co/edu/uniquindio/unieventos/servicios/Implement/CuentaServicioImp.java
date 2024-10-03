package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;

public class CuentaServicioImp implements CuentaServicio {

    private CuentaRepository Cuentarepo;

    @Override
    public void crearCuenta(DtoCuenta cuenta) {

        new Cuenta()

        Cuentarepo.save(cuenta);
        //TODO Crear logica de creacion de cuenta

    }
}
