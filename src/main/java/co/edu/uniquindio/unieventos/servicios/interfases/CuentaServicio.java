package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CuentaServicio {

    String crearCuenta(DTOCrearCuenta cuenta) throws Exception;

    // Método para eliminar una cuenta por su ID
    void eliminarCuenta(String idUsuario)throws Exception;

    // Método para actualizar una cuenta
    void actualizarCuenta(DTOActualizarCuenta cuentaActualizada) throws Exception;

    void verificarCodigo(String idUsuario, CodigoVerificacionDTO codigoVerificacionDTO) throws Exception;

    void activarCuenta(CorreoActivaDTO correo) throws Exception;

    // Método para obtener todas las cuentas
    List<CuentaListadaDTO> obtenerTodasLasCuentas()throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;

    void  reenviarToken(CorreoDTO correo) throws Exception;

    void subirImagenPerfilUsuario(String usuarioId, MultipartFile imagen) throws Exception;

    void restablecerContrasena(RestablecerContrasenaDTO restablecerContrasenaDTO) throws Exception;

    String enviarToken(CorreoDTO correo) throws Exception;

    CuentaObtenidaClienteDTO obtenerCuentaId(String idUsuario) throws Exception;

    void suspenderCuenta(LoginDTO loginDTO);

    String enviarTokenRestablecer(CorreoDTO correo) throws Exception;
}
