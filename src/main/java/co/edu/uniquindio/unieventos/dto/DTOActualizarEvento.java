package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record DTOActualizarEvento(
        @NotBlank @Length(max = 50) String nombre,
        @NotBlank @Length(max = 700) String descripcion,
        @NotNull MultipartFile imagenPoster,
        @NotNull EstadoCuenta estadoEvento,
        @NotEmpty List<DTOSubEventos> subEventos // Aquí agregamos la lista de subeventos
) {
}
