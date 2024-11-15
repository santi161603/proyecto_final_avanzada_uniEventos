package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record SubEventoObtenidoDto(
        int idSubEvento,
        @NotNull LocalDate fechaEvento,
        @NotNull String localidad,
        @NotNull String horaEvento,
        @Min(1) int cantidadEntradas,
        @Min(100) double precioEntrada,
        @NotNull EstadoCuenta estadoSubEvento
        ) {}
