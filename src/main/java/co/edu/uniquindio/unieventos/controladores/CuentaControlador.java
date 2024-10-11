package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/servicios/cuenta-no-autenticada")
@RequiredArgsConstructor
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

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
    public ResponseEntity<MensajeDTO<String>> reenviarToken(String idUsuario) throws Exception{
        cuentaServicio.reenviarToken(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Token reenviado"));
    }

}
