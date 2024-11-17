package co.edu.uniquindio.unieventos.controladores;
import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.servicios.interfases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-administrador")
public class AdministradorControlador {

    private final CuentaServicio cuentaAdministrador;
    private final EventoServicio eventoAdministrador;
    private final LocalidadServicio localidadServicio;
    private final CuponServicio cuponServicio;
    private final ImagenesServicio imagenesServicio;
    private final OrdenServicio ordenServicio;

    @PostMapping("/crear-evento")
    public ResponseEntity<MensajeDTO<String>> crearEvento(@Valid @RequestBody DTOCrearEvento dtoCrearEvento) throws Exception {
        String result = eventoAdministrador.crearEvento(dtoCrearEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @PutMapping("/actualizar-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String idEvento, @Valid @RequestBody DTOActualizarEvento dtoActualizarEvento) throws Exception {
        String result = eventoAdministrador.actualizarEvento(idEvento, dtoActualizarEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @DeleteMapping("/eliminar-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String idEvento) throws Exception {
        eventoAdministrador.eliminarEvento(idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Evento eliminado"));
    }

    @GetMapping("/obtener-todas-las-cuentas")
    public ResponseEntity<MensajeDTO<List<CuentaListadaDTO>>> obtenerTodasLasCuentas() throws Exception{
        List<CuentaListadaDTO> cuentaList = cuentaAdministrador.obtenerTodasLasCuentas();
        return ResponseEntity.ok(new MensajeDTO<>(false,cuentaList));
    }

    @PostMapping("/crear-localidad")
    public ResponseEntity<MensajeDTO<String>> crearLocalidad(@Valid @RequestBody DTOCrearLocalidad localidad) throws Exception {
        localidadServicio.crearLocalidad(localidad);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad creada exitosamente"));
    }

    @PostMapping("/crear-cupon")
    public ResponseEntity<MensajeDTO<String>> crearCupon(@Valid @RequestBody DTOCrearCupon cupon) throws Exception {
        cuponServicio.crearCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon creado exitosamente"));
    }

    @PutMapping("/actualizar-localidad/{idLocalidad}")
    public ResponseEntity<MensajeDTO<String>> actualizarLocalidad(@Valid @RequestBody DTOActualizarLocalidad localidad,
                                                                @PathVariable String idLocalidad) throws Exception {
        localidadServicio.actualizarLocalidad(localidad, idLocalidad);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad actualizada exitosamente"));
    }

    @PutMapping("/actualizar-cupon/{idCupon}")
    public ResponseEntity<MensajeDTO<String>> actualizarCupon(@Valid @RequestBody CuponActualizadoDTO cupon,@PathVariable String idCupon) throws Exception {
        cuponServicio.actualizarCupon(idCupon, cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupon actualizada exitosamente"));
    }

    @DeleteMapping("/eliminar-cupon/{idCupon}")
    public ResponseEntity<MensajeDTO<String>> eliminarCupon(@PathVariable String idCupon) throws Exception {
        cuponServicio.eliminarCupon(idCupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cupón eliminado exitosamente"));
    }


    @DeleteMapping("/eliminar-localidad/{localidadId}")
    public ResponseEntity<MensajeDTO<String>> eliminarLocalidad(@PathVariable String localidadId) throws Exception {
        localidadServicio.eliminarLocalidad(localidadId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad eliminada exitosamente"));
    }

    @PostMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<String>> subirImagen(@RequestParam("imagen") MultipartFile imagen) throws Exception {
        String url = imagenesServicio.subirImagen(imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, url));
    }
    @PutMapping("/actualizar-imagen")
    public ResponseEntity<MensajeDTO<String>> actualizarImagen(@Valid @RequestBody DTOActualizarImagen dtoActualizarImagen) throws Exception {
        imagenesServicio.ActualizarImagen(dtoActualizarImagen.imageName(), dtoActualizarImagen.imagen());
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen actualizada exitosamente"));
    }

    @GetMapping("/obtener-todas-las-ordenes")
    public ResponseEntity<MensajeDTO<List<OrdenInfoDTO>>> obtenerTodasLasOrdenes() throws Exception {
        List<OrdenInfoDTO> ordenInfoDTOList = ordenServicio.obtenerTodasLasOrdenes();
        return ResponseEntity.ok(new MensajeDTO<>(false, ordenInfoDTOList));
    }

    @DeleteMapping("/eliminar-imagen")
    public ResponseEntity<MensajeDTO<String>> eliminarImagen(@Valid @RequestBody String imageName) throws Exception {
        imagenesServicio.eliminarImagen(imageName);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen eliminado exitosamente"));
    }

}
