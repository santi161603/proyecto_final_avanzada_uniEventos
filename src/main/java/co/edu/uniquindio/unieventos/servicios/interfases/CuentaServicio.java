package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.DTOActualizarCuenta;
import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.dto.LoginDTO;
import co.edu.uniquindio.unieventos.dto.TokenDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;

import java.util.List;

public interface CuentaServicio {

    String crearCuenta(DTOCrearCuenta cuenta) throws Exception;

    // Método para eliminar una cuenta por su ID
    Boolean eliminarCuenta(String idUsuario)throws Exception;

    // Método para actualizar una cuenta
    Cuenta actualizarCuenta(DTOActualizarCuenta cuentaActualizada) throws Exception;

    void activarCuenta(String idUsuario, int codigoVerificacionRecibido) throws Exception;

    // Método para obtener todas las cuentas
    List<Cuenta> obtenerTodasLasCuentas()throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;

    void  reenviarToken(String idUsuario) throws Exception;

}
