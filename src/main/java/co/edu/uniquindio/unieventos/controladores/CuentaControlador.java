package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/servicios/cuenta-no-autenticada")
@RequiredArgsConstructor
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;
    private final EventoServicio eventoServicio;

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MensajeDTO<String>> crearCuenta(@Valid @RequestBody DTOCrearCuenta cuenta) throws Exception {
    String info = cuentaServicio.crearCuenta(cuenta);
    return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @PutMapping("/activar-cuenta/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> activarCuenta(@PathVariable String idUsuario,@Valid @RequestBody int codigoVerificacionRecibido) throws Exception {
        cuentaServicio.activarCuenta(idUsuario,codigoVerificacionRecibido);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta activada con exito"));
    }

    @PutMapping("/reenviar-token")
    public ResponseEntity<MensajeDTO<String>> reenviarToken( String idUsuario) throws Exception{
        cuentaServicio.reenviarToken(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token reenviado"));
    }

    @GetMapping("/obtener-evento")
    public ResponseEntity<MensajeDTO<Evento>> obtenerEvento(@PathVariable String idEvento) throws Exception{
        Evento evento = eventoServicio.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false,evento));
    }

    @GetMapping("/obtener-todos-los-eventos")
    public ResponseEntity<MensajeDTO<List<Evento>>> obtenerTodosLosEventos() throws Exception{
        List<Evento> eventosList = eventoServicio.obtenerTodosLosEventos();
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosList));
    }

    @GetMapping("/obtener-todos-los-eventos-Por-Categoria")
    public ResponseEntity<MensajeDTO<List<Evento>>> obtenerEventosPorCategoria(TipoEvento evento) throws Exception{
        List<Evento> eventosCategoriaList = eventoServicio.obtenerEventoCategoria(evento);
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosCategoriaList));
    }


}

