package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class CuentaServicioTest implements CuentaServicio {

    private CuentaRepository cuentaRepo;

    @Override
    public String crearCuenta(DTOCrearCuenta cuenta) throws Exception {

        //TODO implementar codigo test

        Cuenta newCuenta = new Cuenta();

        //TODO este es un ejemplo, se tiene que hacer sobre nuestro modelo de cuenta
        //TODO recordar que el DTO tambien toca quemarlo para el test
        
        /*
        nuevaCuenta.setEmail( cuenta.email() );
        nuevaCuenta.setPassword( cuenta.password() );
        nuevaCuenta.setRol( Rol.CLIENTE );
        nuevaCuenta.setFechaRegistro( LocalDateTime.now() );
        nuevaCuenta.setUsuario( new Usuario(
           cuenta.cedula(),
           cuenta.nombre(),
           cuenta.telefono(),
           cuenta.direccion()
                ));
        nuevaCuenta.setEstado( EstadoCuenta.INACTIVO );*/

        return "";
    }
}

