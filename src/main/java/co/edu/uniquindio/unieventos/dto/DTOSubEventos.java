package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Min;
import org.bson.types.ObjectId;
import org.checkerframework.checker.units.qual.Time;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DTOSubEventos(

        @NotNull LocalDate fechaEvento,
        @NotNull String localidad,
        @NotNull String horaEvento,
        @Min(1) int cantidadEntradas,
        @Min(100) double precioEntrada,
        @NotNull EstadoCuenta estadoSubEvento

        ) {
}
