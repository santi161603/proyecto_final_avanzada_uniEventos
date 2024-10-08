package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.vo.Usuario;
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

    public String crearCuenta(DTOCrearCuenta dtoCrearCuenta) {
        // Crear una nueva instancia de Cuenta
        Cuenta nuevaCuenta = new Cuenta();

        // Asignar los valores del DTO a la entidad Cuenta
        nuevaCuenta.setEmail(dtoCrearCuenta.email());
        nuevaCuenta.setContrasena(dtoCrearCuenta.contrasena());
        nuevaCuenta.setRol(dtoCrearCuenta.rol());
        nuevaCuenta.setEstado(EstadoCuenta.INACTIVO);  // Por defecto inactivo

        // Crear una instancia de Usuario dentro de Cuenta
        Usuario usuario = new Usuario();
        usuario.setCedula(dtoCrearCuenta.cedula());
        usuario.setNombre(dtoCrearCuenta.nombre());
        usuario.setDireccion(dtoCrearCuenta.direccion());
        usuario.setTelefono(dtoCrearCuenta.telefono());

        nuevaCuenta.setUsuario(usuario);

        // Guardar la cuenta en la base de datos
        Cuentarepo.save(nuevaCuenta);

        // Retornar el ID del usuario creado
        return nuevaCuenta.getIdUsuario();
    }
}
