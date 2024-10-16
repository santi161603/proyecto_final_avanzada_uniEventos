package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.DTOActualizarCuenta;
import co.edu.uniquindio.unieventos.dto.MensajeDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-autenticada")
public class CuentaAutenticadaControlador {

    private final CuentaServicio cuentaServicio;

    @DeleteMapping("/eliminar-cuenta-definitivo/{idUsuario}")
    public ResponseEntity<MensajeDTO<Boolean>> eliminarCuenta(@PathVariable String idUsuario) throws Exception {
        Boolean result = cuentaServicio.eliminarCuenta(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, result));
    }
    @PutMapping("/actualizar-cuenta")
    public ResponseEntity<MensajeDTO<String>> actualizarCuenta(@Valid @RequestBody DTOActualizarCuenta cuentaActualizada) throws Exception {
        cuentaServicio.actualizarCuenta(cuentaActualizada);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta actualizada con exito"));
    }

    @GetMapping("/obtener-todas-las-cuentas")
    public ResponseEntity<MensajeDTO<List<Cuenta>>> obtenerTodasLasCuentas() throws Exception{
        List<Cuenta> cuentaList = cuentaServicio.obtenerTodasLasCuentas();
        return ResponseEntity.ok(new MensajeDTO<>(false,cuentaList));
    }
}
