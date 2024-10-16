package co.edu.uniquindio.unieventos.config;

import co.edu.uniquindio.unieventos.dto.MensajeDTO;
import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Configuración de cabeceras para CORS
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return; // Detener la cadena aquí si es un preflight request
        }


        // Obtener la URI de la petición que se está realizando
        String requestURI = request.getRequestURI();

        List<String> urisPublicas = Arrays.asList("/servicios/autenticacion", "/servicios/cuenta-no-autenticada", "/swagger-ui.html", "/swagger-ui/", "/v3/api-docs/","/swagger-ui/index.html","/swagger-ui/swagger-initializer.js","/v3/api-docs");
        List<String> urisclientes = Arrays.asList("/servicios/cuenta-autenticada");
        // Si la URI solicitada es pública, no validar el token, simplemente continuar
        if (urisPublicas.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response); // Continuar sin validación
            return;
        }

        // Se obtiene el token de la petición del encabezado del mensaje HTTP
        String token = getToken(request);
        try {

            // Si no hay token y no es una ruta pública, rechazar el acceso
            if (token == null) {
                crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
                return;
            }

            // Validar acceso según el rol
            if (!validarToken(token, RolUsuario.ADMINISTRADOR)) {
                // Si el usuario es administrador, permitir acceso a todas las rutas
                filterChain.doFilter(request, response);
            } else if (!validarToken(token, RolUsuario.CLIENTE)) {
                // Si el usuario es cliente, permitir acceso solo a rutas públicas y de cliente
                if (urisclientes.stream().anyMatch(requestURI::startsWith) || urisPublicas.stream().anyMatch(requestURI::startsWith)) {
                    filterChain.doFilter(request, response);
                } else {
                    crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
                }
            } else {
                // Si el rol del token no es ni administrador ni cliente, rechazar el acceso
                crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
            }

        } catch (MalformedJwtException | SignatureException e) {
            crearRespuestaError("El token es incorrecto", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
            return; // Detener el flujo si hay un error en el token
        } catch (ExpiredJwtException e) {
            crearRespuestaError("El token está vencido", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
            return; // Detener el flujo si el token está expirado
        } catch (Exception e) {
            crearRespuestaError(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
            return; // Detener el flujo si hay otro error
        }

        // Si no hay errores, se continúa con la petición
        // filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }


    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);


        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }


    private boolean validarToken(String token, RolUsuario rol) {
        boolean error = true;
        if (token != null) {
            Jws<Claims> jws = jwtUtils.parseJwt(token);
            if (RolUsuario.valueOf(jws.getPayload().get("rol").toString()) == rol) {
                error = false;
            }
        }
        return error;
    }


}

