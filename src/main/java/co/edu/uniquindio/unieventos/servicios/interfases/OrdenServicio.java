package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.DTOActualizarOrden;
import co.edu.uniquindio.unieventos.dto.DTOCrearOrden;
import co.edu.uniquindio.unieventos.dto.OrdenInfoDTO;
//import java.util.Map;
//import com.mercadopago.resources.preference.Preference;

import java.util.List;

public interface OrdenServicio {

    // Método para crear una orden
    String crearOrden(DTOCrearOrden orden) throws Exception;

    // Método para eliminar una orden por su ID
    void eliminarOrden(String idOrden) throws Exception;

    // Método para actualizar una orden
    DTOActualizarOrden actualizarOrden(String idOrden, DTOActualizarOrden ordenActualizada) throws Exception;

    // Método para obtener una orden por su ID
    OrdenInfoDTO obtenerOrdenPorId(String idOrden) throws Exception;

    // Método para obtener todas las órdenes
    List<OrdenInfoDTO> obtenerTodasLasOrdenes() throws Exception;

    //metodo para crear una orden atravez del carrito
    String crearOrdenDesdeCarrito(String idCarrito) throws Exception;


//    //Realizar pago
//    Preference realizarPago(String idOrden) throws Exception;
//
//    void recibirNotificacionMercadoPago(Map<String, Object> request);

}

