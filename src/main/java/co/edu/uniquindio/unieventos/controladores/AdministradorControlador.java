package co.edu.uniquindio.unieventos.controladores;
import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-autenticada")
public class AdministradorControlador {

    private final CuentaServicio cuentaAdministrador;
    private final EventoServicio eventoAdministrador;
    private final LocalidadServicio localidadServicio;
    private final ImagenesServicio imagenesServicio;

    @PostMapping("/crear-evento")
    public ResponseEntity<MensajeDTO<String>> crearEvento(@Valid @RequestBody DTOCrearEvento dtoCrearEvento) throws Exception {
        String result = eventoAdministrador.crearEvento(dtoCrearEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @PutMapping("/actualizar-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String idEvento, DTOActualizarEvento dtoActualizarEvento) throws Exception {
        String result = eventoAdministrador.actualizarEvento(idEvento, dtoActualizarEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @DeleteMapping("/eliminar-evento/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String idEvento) throws Exception {
        eventoAdministrador.eliminarEvento(idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Evento eliminado"));
    }

    @DeleteMapping("/eliminar-cuenta-definitivo/{idUsuario}")
    public ResponseEntity<MensajeDTO<Boolean>> eliminarCuenta(@PathVariable String idUsuario) throws Exception {
        Boolean result = cuentaAdministrador.eliminarCuenta(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @GetMapping("/obtener-todas-las-cuentas")
    public ResponseEntity<MensajeDTO<List<Cuenta>>> obtenerTodasLasCuentas() throws Exception{
        List<Cuenta> cuentaList = cuentaAdministrador.obtenerTodasLasCuentas();
        return ResponseEntity.ok(new MensajeDTO<>(false,cuentaList));
    }

    @PostMapping("/crear-localidad")
    public ResponseEntity<MensajeDTO<String>> crearLocalidad(@Valid @RequestBody DTOCrearLocalidad localidad) throws Exception {
        localidadServicio.crearLocalidad(localidad);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad creada exitosamente"));
    }

    @PutMapping("/actualizar-localidad/{localidadId}")
    public ResponseEntity<MensajeDTO<String>> actualizarLocalidad(@Valid @RequestBody DTOActualizarLocalidad localidad,
                                                                @PathVariable String localidadId) throws Exception {
        localidadServicio.actualizarLocalidad(localidad, localidadId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad actualizada exitosamente"));
    }

    @DeleteMapping("/eliminar-localidad/{localidadId}")
    public ResponseEntity<MensajeDTO<String>> eliminarLocalidad(@PathVariable String localidadId) throws Exception {
        localidadServicio.eliminarLocalidad(localidadId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Localidad eliminada exitosamente"));
    }

    @PostMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<String>> subirImagen(@Valid @RequestBody MultipartFile imagen) throws Exception {
        imagenesServicio.subirImagen(imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen Subida con exito"));
    }

    @PutMapping("/actualizar-imagen")
    public ResponseEntity<MensajeDTO<String>> actualizarImagen(@Valid @RequestBody String imageName, @Valid @RequestBody MultipartFile imagen) throws Exception {
        imagenesServicio.ActualizarImagen(imageName, imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen actualizada exitosamente"));
    }

    @DeleteMapping("/eliminar-imagen")
    public ResponseEntity<MensajeDTO<String>> eliminarImagen(@Valid @RequestBody String imageName) throws Exception {
        imagenesServicio.eliminarImagen(imageName);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Imagen eliminado exitosamente"));
    }

}
