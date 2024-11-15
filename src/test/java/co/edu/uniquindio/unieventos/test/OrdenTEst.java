package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.dto.ItemCarritoDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Orden;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.modelo.vo.Pago;
import co.edu.uniquindio.unieventos.modelo.vo.Transaccion;
import co.edu.uniquindio.unieventos.repositorio.OrdenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrdenTEst {

    @Autowired
    private OrdenRepository ordenRepository;

    @Test
    public void crearOrden(){

        Orden orden = new Orden();
        Transaccion transaccion = new Transaccion();
        Pago pago = new Pago();
        transaccion.setIdCliente("672cfb5985c36067a89f4ff8");

        ItemCarritoVO item1 = new ItemCarritoVO();
        item1.setEventoId("6731305d41826547298c4b56");
        item1.setIdSubevento(1);
        item1.setCantidadEntradas(2);

        List<ItemCarritoVO> items = new ArrayList<>();
        items.add(item1);

        ItemCarritoVO item2 = new ItemCarritoVO();
        item2.setEventoId("6731305d41826547298c4b56");
        item2.setIdSubevento(2);
        item2.setCantidadEntradas(3);

        items.add(item2);

        transaccion.setProductos(items);
        pago.setMetodoPago("MercadoPago");

        orden.setTransaccion(transaccion);

        String id = ordenRepository.save( orden ).getIdOrden();
        System.out.println(id);
    }

}
