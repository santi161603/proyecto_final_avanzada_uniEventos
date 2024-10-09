package co.edu.uniquindio.unieventos.servicios.interfases;

public interface CuponServicio {

    // Método para crear un cupón
    String crearCupon(DTOCrearCupon cupon) throws Exception;

    // Método para eliminar un cupón por su ID
    void eliminarCupon(String idCupon) throws Exception;

    // Método para actualizar un cupón
    Cupon actualizarCupon(String idCupon, DTOActualizarCupon cuponActualizado) throws Exception;

    // Método para obtener un cupón por su ID
    Cupon obtenerCuponPorId(String idCupon) throws Exception;

    // Método para obtener todos los cupones
    List<Cupon> obtenerTodosLosCupones() throws Exception;

}

