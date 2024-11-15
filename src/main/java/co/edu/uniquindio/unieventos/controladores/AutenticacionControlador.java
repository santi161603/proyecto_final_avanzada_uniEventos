package co.edu.uniquindio.unieventos.controladores;

import co.edu.uniquindio.unieventos.config.JWTUtils;
import co.edu.uniquindio.unieventos.dto.LoginDTO;
import co.edu.uniquindio.unieventos.dto.MensajeDTO;
import co.edu.uniquindio.unieventos.dto.TokenDTO;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/autenticacion")
public class AutenticacionControlador {

    private  final CuentaServicio cuentaServicio;
    private final JWTUtils jwtTokenProvider;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = cuentaServicio.iniciarSesion(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, tokenDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenDTO tokenDTO) {
        System.out.println("Estoy aqui");
        try {
            String refreshedToken = jwtTokenProvider.refreshToken(tokenDTO.token()); // Asegúrate de usar el getter
            return ResponseEntity.ok(new TokenDTO(refreshedToken)); // Enviar el nuevo token en la respuesta
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            // Manejo de excepciones más específico para casos de tokens inválidos
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido o caducado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al refrescar el token");
        }
    }


}

