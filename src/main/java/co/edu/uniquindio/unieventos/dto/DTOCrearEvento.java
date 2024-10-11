package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.subEvento;
import jakarta.mail.Multipart;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record DTOCrearEvento (
        @NotBlank @Length(max = 50) String nombre,
        @NotBlank @Length(max = 15) String ciudad,
        @NotBlank @Length(max = 700) String descripcion,
        @NotNull TipoEvento tipoEvento, // concierto, teatro, deporte, etc.
        @NotNull MultipartFile imagenPoster,
        @NotEmpty List<DTOSubEventos> subEventos // Aquí agregamos la lista de subeventos

){
}
