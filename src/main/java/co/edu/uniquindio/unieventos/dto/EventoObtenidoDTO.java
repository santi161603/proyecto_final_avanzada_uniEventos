package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record EventoObtenidoDTO(
        @NotBlank @Length(max = 50) String nombre,
        @NotNull Ciudades ciudad,
        @NotBlank @Length(max = 700) String descripcion,
        @NotNull TipoEvento tipoEvento, // concierto, teatro, deporte, etc.
        @NotEmpty List<DTOSubEventos> subEventos, // Aquí agregamos la lista de subeventos
        @NotNull String imagenPoster

) {
}
