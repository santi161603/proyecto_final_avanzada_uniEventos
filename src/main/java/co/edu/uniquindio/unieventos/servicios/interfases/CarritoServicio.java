package co.edu.uniquindio.unieventos.servicios.interfases;


import co.edu.uniquindio.unieventos.dto.DTOActualizarCarrito;
import co.edu.uniquindio.unieventos.dto.DTOCrearCarrito;
import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import org.bson.types.ObjectId;

import java.util.List;

public interface CarritoServicio {

    // Método para crear un carrito
    String crearCarrito(String usuarioId) throws Exception;

    //Metodo para añadir items al carrito
    void anadirItem(List<ObjectId> items, ObjectId usuarioId ) throws Exception;

    // Método para eliminar un item del carrito
    void eliminarItem(ObjectId item,ObjectId usuarioId) throws Exception;

    // Método para obtener lista de items
    List<ObjectId> obtenerListaItems(ObjectId usuarioId) throws Exception;

    // Limpiar carrito
    void limpiarCarrito(ObjectId usuarioId) throws Exception;

}







