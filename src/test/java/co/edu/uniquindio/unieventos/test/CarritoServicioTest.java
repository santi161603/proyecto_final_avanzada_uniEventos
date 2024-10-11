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

      String carritoId = carritoServicio.crearCarrito("67084a0bb792395ec850e773");

      assertNotNull(carritoId);

    }
}
