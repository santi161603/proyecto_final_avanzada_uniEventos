package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarCuenta;
import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.vo.CodigoVerificacion;
import co.edu.uniquindio.unieventos.modelo.vo.Usuario;
import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServicioImp implements CuentaServicio {

    private final CuentaRepository Cuentarepo;

    @Override
    public String crearCuenta(DTOCrearCuenta dtoCrearCuenta) {
        // Crear una nueva instancia de Cuenta
        Cuenta nuevaCuenta = new Cuenta();
        // Asignar los valores del DTO a la entidad Cuenta
        nuevaCuenta.setRol(dtoCrearCuenta.rol());
        nuevaCuenta.setEstado(EstadoCuenta.INACTIVO);

        int codigoVerificacion = generarCodigoVerificacion();

        CodigoVerificacion codigoVerif = new CodigoVerificacion();
        codigoVerif.setCodigo(codigoVerificacion);
        codigoVerif.setFecha(LocalDateTime.now());

        // Crear una instancia de Usuario dentro de Cuenta
        Usuario usuario = new Usuario();
        usuario.setCedula(dtoCrearCuenta.cedula());
        usuario.setNombre(dtoCrearCuenta.nombre());
        usuario.setDireccion(dtoCrearCuenta.direccion());
        usuario.setTelefono(dtoCrearCuenta.telefono());

        nuevaCuenta.setUsuario(usuario);
        nuevaCuenta.setCodigoVerificacion(codigoVerif);

        // Guardar la cuenta en la base de datos
        Cuentarepo.save(nuevaCuenta);

        // Retornar el ID del usuario creado
        return nuevaCuenta.getIdUsuario();
    }

    private int generarCodigoVerificacion() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Genera un número entre 1000 y 9999
    }

    @Override
    public Boolean eliminarCuenta(String idUsuario) {

        if (Cuentarepo.existsById(idUsuario)) {
            Cuentarepo.deleteById(idUsuario);
            return true;  // La cuenta fue eliminada
        } else {
            return false; // La cuenta no fue encontrada
        }
    }

    @Override
    public Cuenta actualizarCuenta(String idUsuario, DTOActualizarCuenta cuentaActualizada) {
        Optional<Cuenta> cuentaOpt = Cuentarepo.findById(idUsuario);
        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();

            // Actualizar la información del usuario
            Usuario usuario = cuenta.getUsuario();
            usuario.setCedula(cuentaActualizada.cedula());
            usuario.setNombre(cuentaActualizada.nombre());
            usuario.setDireccion(cuentaActualizada.direccion());
            usuario.setTelefono(cuentaActualizada.telefono());

            cuenta.setUsuario(usuario);

            // Guardar la cuenta actualizada en la base de datos
            return Cuentarepo.save(cuenta);
        } else {
            throw new EntityNotFoundException("No se encontró la cuenta con ID: " + idUsuario);
        }
    }

    // Obtener todas las cuentas
    @Override
    public List<Cuenta> obtenerTodasLasCuentas() {
        // Devolver todas las cuentas almacenadas en la base de datos
        return Cuentarepo.findAll();
    }


}
