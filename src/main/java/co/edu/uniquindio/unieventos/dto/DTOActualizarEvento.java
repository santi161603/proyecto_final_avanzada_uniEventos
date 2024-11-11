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
         @Length(max = 50) String nombre,
         @Length(max = 700) String descripcion,
         String imagenPoster,
         TipoEvento tipoEvento,
         EstadoCuenta estadoEvento,
         List<SubEventoObtenidoDto> subEventos // Aquí agregamos la lista de subeventos
) {
}
