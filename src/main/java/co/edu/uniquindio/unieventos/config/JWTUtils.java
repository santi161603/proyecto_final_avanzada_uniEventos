package co.edu.uniquindio.unieventos.config;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    public String generarToken(String email, Map<String, Object> claims){

        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1L, ChronoUnit.HOURS)))
                .signWith( getKey() )
                .compact();
    }

    public String refreshToken(String refreshToken) {
        System.out.println("Estoy aqui");
        try {
            // Validar el refreshToken
            Jws<Claims> claims = parseJwt(refreshToken); // Asegúrate de que `parseJwt` maneje bien las excepciones
            String email = claims.getBody().getSubject();

            // Generar un nuevo access token con la misma información
            Map<String, Object> claimsMap = claims.getBody();
            return generarToken(email, claimsMap); // Aquí generas el nuevo token
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            throw new RuntimeException("El refresh token es inválido o caducado", e);
        }
    }

    public Jws<Claims> parseJwt(String jwtString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        JwtParser jwtParser = Jwts.parser().verifyWith( getKey() ).build();
        return jwtParser.parseSignedClaims(jwtString);
    }

    private SecretKey getKey(){
        String claveSecreta = "yL3$8sZ#U7@bP9mQ&dJfX2rT6wA*oI!1gVk5C%H";
        byte[] secretKeyBytes = claveSecreta.getBytes();
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }


}
