package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-no-autenticada")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;
    private final EventoServicio eventoServicio;
    private final LocalidadServicio localidadServicio;

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MensajeDTO<String>> crearCuenta(@Valid @RequestBody DTOCrearCuenta cuenta) throws Exception {
        String idUsuario = cuentaServicio.crearCuenta(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, idUsuario));
    }

    @PutMapping("/activar-cuenta/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> activarCuenta(@PathVariable String idUsuario,@Valid @RequestBody CodigoVerificacionDTO codigoVerificacionDTO ) throws Exception {
        cuentaServicio.activarCuenta(idUsuario,codigoVerificacionDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta activada con exito"));
    }

    @PutMapping("/reenviar-token/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> reenviarToken(@PathVariable String idUsuario) throws Exception{
        cuentaServicio.reenviarToken(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token reenviado"));
    }

    @PutMapping("/enviar-token-recuperar")
    public ResponseEntity<MensajeDTO<String>> enviarTokenRecuperar(@Valid @RequestBody String correo) throws Exception{
        cuentaServicio.enviarToken(correo);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token enviado"));
    }

    @PutMapping("/restablecer-contrasena")
    public ResponseEntity<MensajeDTO<String>> restablecerContrasena(@Valid @RequestBody RestablecerContrasenaDTO restablecerContrasenaDTO) throws Exception{
        cuentaServicio.restablecerContrasena(restablecerContrasenaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Contraseña cambiada con exito"));
    }

    @GetMapping("/obtener-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<EventoObtenidoDTO>> obtenerEvento(@PathVariable String idEvento) throws Exception {
        EventoObtenidoDTO evento = eventoServicio.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, evento));
    }

    @GetMapping("/obtener-todos-eventos")
    public ResponseEntity<MensajeDTO<List<EventoObtenidoDTO>>> obtenerEvento() throws Exception {
        List<EventoObtenidoDTO> evento = eventoServicio.obtenerTodosLosEventos();
        return ResponseEntity.ok(new MensajeDTO<>(false, evento));
    }

    @GetMapping("/obtener-todos-los-eventos-Por-Categoria")
    public ResponseEntity<MensajeDTO<List<EventoObtenidoDTO>>> obtenerEventosPorCategoria(@Valid @RequestBody TipoEvento evento) throws Exception{
        List<EventoObtenidoDTO> eventosCategoriaList = eventoServicio.obtenerEventoCategoria(evento);
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosCategoriaList));
    }

    @GetMapping("/obtener-todas-localidad")
    public ResponseEntity<MensajeDTO<List<LocalidadEventoObtenidoDTO>>> obtenerLocalidades() throws Exception {
        List<LocalidadEventoObtenidoDTO> localidades = localidadServicio.obtenerLocalidades();
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }

    @GetMapping("/obtener-por-id-localidad/{localidadId}")
    public ResponseEntity<MensajeDTO<LocalidadEventoObtenidoDTO>> obtenerLocalidadPorId(@PathVariable String localidadId) throws Exception {
        LocalidadEventoObtenidoDTO localidad = localidadServicio.obtenerLocalidadPorId(localidadId);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidad));
    }

    @GetMapping("/obtener-por-ciudad-localidad")
    public ResponseEntity<MensajeDTO<List<LocalidadEventoObtenidoDTO>>> obtenerLocalidadesPorCiudad(@Valid @RequestBody Ciudades ciudad) throws Exception {
        List<LocalidadEventoObtenidoDTO> localidades = localidadServicio.obtenerLocalidadesPorCiudad(ciudad);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }


}


