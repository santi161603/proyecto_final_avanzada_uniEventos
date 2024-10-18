package co.edu.uniquindio.unieventos.servicios.interfases;

import java.util.List;

public interface CarritoServicio {

    // Método para crear un carrito
    String crearCarrito(String usuarioId) throws Exception;

    //Metodo para añadir items al carrito
    void anadirItem(List<String> items, String usuarioId ) throws Exception;

    // Método para eliminar un item del carrito
    void eliminarItem(List<String> item,String usuarioId) throws Exception;

    // Método para obtener lista de items
    List<String> obtenerListaItems(String usuarioId) throws Exception;

    // Limpiar carrito
    void limpiarCarrito(String usuarioId) throws Exception;

}







