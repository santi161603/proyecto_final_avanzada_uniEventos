package co.edu.uniquindio.unieventos.dto;

public record ItemCarritoDTO(
        String eventoId,
        int idSubevento,
        int cantidadEntradas,
        String cupon
) {
}
