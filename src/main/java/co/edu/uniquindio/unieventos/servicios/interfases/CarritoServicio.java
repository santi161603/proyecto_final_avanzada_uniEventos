package co.edu.uniquindio.unieventos.servicios.interfases;



public interface CarritoServicio {

    // Método para crear un carrito
    String crearCarrito(DTOCrearCarrito carrito) throws Exception;

    // Método para eliminar un carrito por su ID
    void eliminarCarrito(String idCarrito) throws Exception;

    // Método para actualizar un carrito
    Carrito actualizarCarrito(String idCarrito, DTOActualizarCarrito carritoActualizado) throws Exception;

    // Método para obtener un carrito por su ID
    Carrito obtenerCarritoPorId(String idCarrito) throws Exception;

    // Método para obtener todos los carritos
    List<Carrito> obtenerTodosLosCarritos() throws Exception;

    // Método para recibir un evento relacionado con el carrito
    void procesarEvento(DTOEventoCarrito evento) throws Exception;
}







