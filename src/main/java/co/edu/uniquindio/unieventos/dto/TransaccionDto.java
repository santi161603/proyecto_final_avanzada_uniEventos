package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TransaccionDto(

        @NotEmpty(message = "La lista de productos no puede estar vacía")
        List<ItemCarritoDTO> productos,

        @NotBlank(message = "El ID del cliente no puede estar en blanco")
        String idCliente,

        String qR
) {
}
