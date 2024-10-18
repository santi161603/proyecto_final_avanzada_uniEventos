package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarCarrito;
import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.repositorio.CarritoComprasRepository;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServicioImp implements CarritoServicio {

    private final CarritoComprasRepository carritoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public String crearCarrito(String usuarioId) throws Exception {
        CarritoCompras carrito = new CarritoCompras();

        carrito.setUsuarioId(usuarioId);
        carrito.setItems(new ArrayList<>());

        carritoRepository.save(carrito);

        return carrito.getIdCarritoCompras();
    }

    @Override
    public void anadirItem(List<String> items, String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Añadir todos los items al carrito (suponiendo que 'items' es una lista de IDs de eventos u objetos)
        for (String itemId : items) {
            // Comprobar que el evento/item existe (opcional, depende de tu lógica)
            Optional<Evento> eventoOptional = eventoRepository.findById(String.valueOf(itemId));

            if (eventoOptional.isEmpty()) {
                throw new Exception("Item con ID " + itemId + " no encontrado");
            }

            // Añadir el item al carrito
            carritoCompras.getItems().add(itemId);
        }

        // Guardar el carrito actualizado con los items añadidos
        carritoRepository.save(carritoCompras);
    }


    @Override
    public void eliminarItem(List<String> items, String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Verificar si la lista de items no está vacía
        if (items == null || items.isEmpty()) {
            throw new Exception("La lista de items a eliminar está vacía.");
        }

        // Eliminar los items de la lista del carrito
        for (String item : items) {
            if (carritoCompras.getItems().contains(item)) {
                carritoCompras.getItems().remove(item);
            } else {
                throw new Exception("Item con ID " + item + " no encontrado en el carrito del usuario: " + usuarioId);
            }
        }

        // Guardar el carrito actualizado sin el item
        carritoRepository.save(carritoCompras);
    }

    @Override
    public List<String> obtenerListaItems(String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID de usuario
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Obtener la lista de items (IDs de los eventos) en el carrito
        List<String> items = carritoCompras.getItems();

        // Si el carrito está vacío, devolver una lista vacía o manejar como sea necesario
        if (items.isEmpty()) {
            throw new Exception("El carrito está vacío para el usuario: " + usuarioId);
        }

        // Devolver la lista de items
        return items;
    }


    @Override
    public void limpiarCarrito(String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID de usuario
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Limpiar la lista de items
        carritoCompras.getItems().clear();

        // Guardar el carrito actualizado (vacío)
        carritoRepository.save(carritoCompras);
    }


}
