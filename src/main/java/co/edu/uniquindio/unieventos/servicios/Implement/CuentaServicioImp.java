package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServicioImp implements CuentaServicio {

    private CuentaRepository Cuentarepo;

    @Override
    public String crearCuenta(DTOCrearCuenta cuenta) {

        Cuenta newCuenta = new Cuenta();

        //TODO Crear logica de creacion de cuenta

        //TODO este es un ejemplo, se tiene que hacer sobre nuestro modelo de cuenta
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

        Cuentarepo.save(newCuenta);
        return newCuenta.getIdUsuario();
    }
}
