package co.edu.uniquindio.unieventos.dto;

import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;

import java.util.List;

public record CarritoObtenidoDTO(
        String idCarritoCompras,
        String usuarioId,
        List<ItemCarritoVO> items,
        Double totalPrecio
) {
}
