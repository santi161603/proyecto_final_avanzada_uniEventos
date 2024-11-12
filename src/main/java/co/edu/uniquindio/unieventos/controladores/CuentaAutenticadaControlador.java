package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.CuponServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/cuenta-autenticada")
public class CuentaAutenticadaControlador {

    private final CuentaServicio cuentaServicio;
    private final CuponServicio cuponServicio;
    private final CarritoServicio carritoServicio;

    @PutMapping("/actualizar-cuenta")
    public ResponseEntity<MensajeDTO<String>> actualizarCuenta(@Valid @RequestBody DTOActualizarCuenta cuentaActualizada) throws Exception {
        cuentaServicio.actualizarCuenta(cuentaActualizada);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta actualizada con exito"));
    }

    @GetMapping("/obtener-todos-los-cupones")
    public ResponseEntity<MensajeDTO<List<CuponObtenidoDTO>>> obtenerTodosLosCupones() throws Exception {
        List<CuponObtenidoDTO> cupones =cuponServicio.obtenerTodosLosCupones();
        return ResponseEntity.ok(new MensajeDTO<>(false, cupones));
    }

    @GetMapping("/obtener-cupon-id/{idCupon}")
    public ResponseEntity<MensajeDTO<CuponObtenidoDTO>> obtenerTodosLosCupones(@PathVariable String idCupon) throws Exception {
        CuponObtenidoDTO cupones = cuponServicio.obtenerCuponPorId(idCupon);
        return ResponseEntity.ok(new MensajeDTO<>(false, cupones));
    }

    @GetMapping("/obtener-cuentaid/{usuarioId}")
    public ResponseEntity<MensajeDTO<CuentaListadaDTO>> obtenerCuentaId(@PathVariable String usuarioId) throws Exception {
        CuentaListadaDTO cuentaListadaDTO = cuentaServicio.obtenerCuentaId(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, cuentaListadaDTO));
    }

    @PostMapping("/crear-carrito/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> crearCarrito(@PathVariable String usuarioId) throws Exception {
        String carritoId = carritoServicio.crearCarrito(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, carritoId));
    }

    @PostMapping("/anadir-item/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> anadirItem(@Valid @RequestBody ItemCarritoDTO items, @PathVariable String usuarioId) throws Exception {
        carritoServicio.anadirItem(items, usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Items añadidos al carrito exitosamente"));
    }

    @PutMapping("/eliminar-item/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> eliminarItem(@Valid @RequestBody ItemCarritoDTO item, @PathVariable String usuarioId) throws Exception {
        carritoServicio.eliminarItem(item, usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Item eliminado del carrito exitosamente"));
    }

    @GetMapping("/obtener-lista-items/{usuarioId}")
    public ResponseEntity<MensajeDTO<List<ItemCarritoVO>>> obtenerListaItems(@PathVariable String usuarioId) throws Exception {
        List<ItemCarritoVO> items = carritoServicio.obtenerListaItems(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, items));
    }

    @DeleteMapping("/limpiar-carrito/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> limpiarCarrito(@PathVariable String usuarioId) throws Exception {
        carritoServicio.limpiarCarrito(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Carrito limpiado exitosamente"));
    }

    @PostMapping("/actualizar-imagen-perfil/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> subirImagenPerdil(@PathVariable String usuarioId,@Valid @RequestBody MultipartFile imagen) throws Exception {
        cuentaServicio.subirImagenPerfilUsuario(usuarioId,imagen);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Imagen de perfil actualizada exitosamente"));
    }

    @GetMapping("/obtener-carrito/{usuarioId}")
    public ResponseEntity<MensajeDTO<CarritoObtenidoDTO>> obtenerCarrito(@PathVariable String usuarioId) throws Exception {
        CarritoObtenidoDTO carritoObtenidoDTO = carritoServicio.obtenerCarrito(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, carritoObtenidoDTO));
    }

    @PutMapping("/reducir-cantidad-item/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> reducirCantidadItemCarrito(@Valid @RequestBody ItemCarritoDTO itemCarritoDTO,@PathVariable String usuarioId) throws Exception {
        carritoServicio.reducirCantidad(itemCarritoDTO,usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cantidad reducida"));
    }

    @PutMapping("/aumentar-cantidad-item/{usuarioId}")
    public ResponseEntity<MensajeDTO<String>> aumentarCantidadItemCarrito(@Valid @RequestBody ItemCarritoDTO itemCarritoDTO,@PathVariable String usuarioId) throws Exception {
        carritoServicio.aumentarCantidad(itemCarritoDTO,usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cantidad reducida"));
    }

}
