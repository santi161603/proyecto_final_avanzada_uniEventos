package co.edu.uniquindio.unieventos.servicios.interfases;


import co.edu.uniquindio.unieventos.dto.DTOActualizarCarrito;
import co.edu.uniquindio.unieventos.dto.DTOCrearCarrito;
import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import org.bson.types.ObjectId;

import java.util.List;

public interface CarritoServicio {

    // Método para crear un carrito
    String crearCarrito(ObjectId usuarioId) throws Exception;

    // Método para eliminar un carrito por su ID
    void eliminarCarrito(String idCarrito) throws Exception;

    // Método para obtener un carrito por su ID
    CarritoCompras obtenerCarritoPorId(String idCarrito) throws Exception;

    // Método para obtener todos los carritos
    List<CarritoCompras> obtenerTodosLosCarritos() throws Exception;

    // Método para recibir un evento relacionado con el carrito
    void añadirEventoCarrito(String idevento, String idCliente) throws Exception;

    void eliminarEventoCarrito(String idEvento, String idCliente) throws Exception;
}







