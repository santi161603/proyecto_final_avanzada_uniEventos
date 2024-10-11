package co.edu.uniquindio.unieventos.dto;

import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Min;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DTOSubEventos(

        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fechaEvento,
        @NotNull ObjectId localidades,
        @Min(1) int cantidadEntradas

) {
}
