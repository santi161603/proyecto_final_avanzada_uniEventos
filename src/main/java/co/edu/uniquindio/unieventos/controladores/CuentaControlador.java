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
@RequestMapping("/servicios/cuenta-no-autenticada")
@RequiredArgsConstructor
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;
    private final EventoServicio eventoServicio;
    private final LocalidadServicio localidadServicio;

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

    @PutMapping("/reenviar-token/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> reenviarToken(@PathVariable String idUsuario) throws Exception{
        cuentaServicio.reenviarToken(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token reenviado"));
    }

    @GetMapping("/obtener-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<Evento>> obtenerEvento(@PathVariable String idEvento) throws Exception {
        Evento evento = eventoServicio.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, evento));
    }

    @GetMapping("/obtener-todos-los-eventos-Por-Categoria")
    public ResponseEntity<MensajeDTO<List<Evento>>> obtenerEventosPorCategoria(@Valid @RequestBody TipoEvento evento) throws Exception{
        List<Evento> eventosCategoriaList = eventoServicio.obtenerEventoCategoria(evento);
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosCategoriaList));
    }

    @GetMapping("/obtener-todas-localidad")
    public ResponseEntity<MensajeDTO<List<LocalidadEvento>>> obtenerLocalidades() throws Exception {
        List<LocalidadEvento> localidades = localidadServicio.obtenerLocalidades();
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }

    @GetMapping("/obtener-por-id-localidad/{localidadId}")
    public ResponseEntity<MensajeDTO<LocalidadEvento>> obtenerLocalidadPorId(@PathVariable String localidadId) throws Exception {
        LocalidadEvento localidad = localidadServicio.obtenerLocalidadPorId(localidadId);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidad));
    }

    @GetMapping("/obtener-por-ciudad-localidad")
    public ResponseEntity<MensajeDTO<List<LocalidadEvento>>> obtenerLocalidadesPorCiudad(@Valid @RequestBody Ciudades ciudad) throws Exception {
        List<LocalidadEvento> localidades = localidadServicio.obtenerLocalidadesPorCiudad(ciudad);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }


}

