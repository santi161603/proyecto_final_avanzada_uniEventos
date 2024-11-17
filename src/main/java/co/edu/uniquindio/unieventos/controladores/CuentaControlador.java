package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.OrdenServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-no-autenticada")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;
    private final EventoServicio eventoServicio;
    private final LocalidadServicio localidadServicio;
    private final OrdenServicio ordenServicio;

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MensajeDTO<String>> crearCuenta(@Valid @RequestBody DTOCrearCuenta cuenta) throws Exception {
        String idUsuario = cuentaServicio.crearCuenta(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, idUsuario));
    }

    @PutMapping("/activar-cuenta")
    public ResponseEntity<MensajeDTO<String>> activarCuenta(@Valid @RequestBody CorreoDTO correo,@Valid @RequestBody CodigoVerificacionDTO codigoVerificacionDTO ) throws Exception {
        cuentaServicio.activarCuenta(correo,codigoVerificacionDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta activada con exito"));
    }



    @PutMapping("/verificar-codigo/{idUsuario}")
    public ResponseEntity<MensajeDTO<String>> verificarCodigo(@PathVariable String idUsuario,@Valid @RequestBody CodigoVerificacionDTO codigoVerificacionDTO ) throws Exception {
        cuentaServicio.verificarCodigo(idUsuario,codigoVerificacionDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Codigo exitoso"));
    }

    @PutMapping("/reenviar-token")
    public ResponseEntity<MensajeDTO<String>> reenviarToken(@Valid @RequestBody CorreoDTO correo) throws Exception{
        cuentaServicio.reenviarToken(correo);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token reenviado"));
    }

    @PutMapping("/enviar-token-recuperar")
    public ResponseEntity<MensajeDTO<String>> enviarTokenRecuperar(@Valid @RequestBody CorreoDTO correo) throws Exception{
        String id = cuentaServicio.enviarToken(correo);
        return ResponseEntity.ok(new MensajeDTO<>(false, id));
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

    @PutMapping("/obtener-todos-los-eventos-por-categoria")
    public ResponseEntity<MensajeDTO<List<EventoObtenidoDTO>>> obtenerEventosPorCategoria(@Valid @RequestBody TipoEventoDTO evento) throws Exception{
        List<EventoObtenidoDTO> eventosCategoriaList = eventoServicio.obtenerEventoCategoria(evento);
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosCategoriaList));
    }

    @PutMapping("/obtener-todos-los-eventos-por-ciudad")
    public ResponseEntity<MensajeDTO<List<EventoObtenidoDTO>>> obtenerEventosPorCiudad(@Valid @RequestBody CiudadesDTO ciudad) throws Exception{
        List<EventoObtenidoDTO> eventosCategoriaList = eventoServicio.obtenerEventosPorCiudad(ciudad.ciudad());
        return ResponseEntity.ok(new MensajeDTO<>(false,eventosCategoriaList));
    }

    @GetMapping("/obtener-todas-localidades")
    public ResponseEntity<MensajeDTO<List<LocalidadEventoObtenidoDTO>>> obtenerLocalidades() throws Exception {
        List<LocalidadEventoObtenidoDTO> localidades = localidadServicio.obtenerLocalidades();
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }

    @GetMapping("/obtener-por-id-localidad/{idLocalidad}")
    public ResponseEntity<MensajeDTO<LocalidadEventoObtenidoDTO>> obtenerLocalidadPorId(@PathVariable String idLocalidad) throws Exception {
        LocalidadEventoObtenidoDTO localidad = localidadServicio.obtenerLocalidadPorId(idLocalidad);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidad));
    }

    @GetMapping("/obtener-por-ciudad-localidad")
    public ResponseEntity<MensajeDTO<List<LocalidadEventoObtenidoDTO>>> obtenerLocalidadesPorCiudad(@Valid @RequestBody Ciudades ciudad) throws Exception {
        List<LocalidadEventoObtenidoDTO> localidades = localidadServicio.obtenerLocalidadesPorCiudad(ciudad);
        return ResponseEntity.ok(new MensajeDTO<>(false, localidades));
    }

    @GetMapping("/obtener-todas-localidades-id-nombre")
    public ResponseEntity<MensajeDTO<List<NombreyIdLocalidadObtenidaDTO>>> obtenerTodasLocalidadesNombreId() throws Exception{
        List<NombreyIdLocalidadObtenidaDTO> cuentaList = localidadServicio.obtenerTodasLasLocalidadesNombreID();
        return ResponseEntity.ok(new MensajeDTO<>(false,cuentaList));
    }

    @PostMapping("/notificacion-mercado-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> request){
        ordenServicio.recibirNotificacionMercadoPago(request);
    }

    @PutMapping("/suspender-cuenta")
    public ResponseEntity<MensajeDTO<String>> suspenderCuenta(@Valid @RequestBody LoginDTO loginDTO){
        cuentaServicio.suspenderCuenta(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(true, "SUSPENDIDA"));
    }

    @PutMapping("/enviar-token-suspe")
    public ResponseEntity<MensajeDTO<String>> suspenderCuenta(@Valid @RequestBody CorreoDTO correoDTO) throws Exception{
        String id = cuentaServicio.enviarTokenRestablecer(correoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, id));
    }



}


