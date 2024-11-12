package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.CarritoObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.ItemCarritoDTO;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;

import java.time.LocalDateTime;
import java.util.List;

public interface CarritoServicio {

    // Método para crear un carrito
    String crearCarrito(String usuarioId) throws Exception;

    //Metodo para añadir items al carrito
    void anadirItem(ItemCarritoDTO items, String usuarioId ) throws Exception;

    // Método para eliminar un item del carrito
    void eliminarItem(ItemCarritoDTO item, String usuarioId) throws Exception;

    // Método para obtener lista de items
    List<ItemCarritoVO> obtenerListaItems(String usuarioId) throws Exception;

    void aumentarCantidad(ItemCarritoDTO items, String usuarioId) throws Exception;

    void reducirCantidad(ItemCarritoDTO items, String usuarioId) throws Exception;

    CarritoObtenidoDTO obtenerCarrito(String usuarioId) throws Exception;

    // Limpiar carrito
    void limpiarCarrito(String usuarioId) throws Exception;

}







