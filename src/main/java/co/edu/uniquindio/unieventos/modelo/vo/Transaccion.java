package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transaccion {

    private List<ItemCarritoVO> productos;
    private String idCliente;
    private String qr;
}
