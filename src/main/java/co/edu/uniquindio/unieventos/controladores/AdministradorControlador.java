package co.edu.uniquindio.unieventos.controladores;
import co.edu.uniquindio.unieventos.dto.DTOActualizarEvento;
import co.edu.uniquindio.unieventos.dto.DTOCrearEvento;
import co.edu.uniquindio.unieventos.dto.MensajeDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-autenticada")
public class AdministradorControlador {

    private final CuentaServicio cuentaAdministrador;
    private final EventoServicio eventoAdministrador;

    @PostMapping("/crear-evento")
    public ResponseEntity<MensajeDTO<String>> crearEvento(DTOCrearEvento dtoCrearEvento) throws Exception {
        String result = eventoAdministrador.crearEvento(dtoCrearEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @PutMapping("/actualizar-evento")
    public ResponseEntity<MensajeDTO<String>> eliminarEvento(@PathVariable String idEvento, DTOActualizarEvento dtoActualizarEvento) throws Exception {
        String result = eventoAdministrador.actualizarEvento(idEvento, dtoActualizarEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }

    @DeleteMapping("/eliminar-evento")
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

}
