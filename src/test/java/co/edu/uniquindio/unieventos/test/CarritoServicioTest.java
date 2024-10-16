package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CarritoServicioTest {

    @Autowired
    private CarritoServicio carritoServicio;

    @Test
    public void crearCarritoTest() throws Exception {

        //aqui le pasamos al crear carrito el id del usuario para asociarselo, el nos retorna el id del carrito
        // luego miramos que non sea nulo
      String carritoId = carritoServicio.crearCarrito("670828b643d3265f08e1e0b6");

      assertNotNull(carritoId);

    }
}
