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

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServicioImp implements CarritoServicio {

    private final CarritoComprasRepository carritoRepository;
    private final EventoRepository eventoRepository;
    
    @Override
    public String crearCarrito(ObjectId usuarioId) throws Exception {
        CarritoCompras carrito = new CarritoCompras();

        carrito.setUsuarioId(usuarioId);

        carritoRepository.save(carrito);

        return carrito.getIdCarritoCompras();
    }

    @Override
    public void eliminarCarrito(String idCarrito) throws Exception {
        CarritoCompras carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        carritoRepository.deleteById(idCarrito);
    }

    @Override
    public CarritoCompras actualizarCarrito(String idCarrito, DTOActualizarCarrito carritoActualizado) throws Exception {

        CarritoCompras carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));


        carrito.setItems(carritoActualizado.Eventoitems());
        carrito.setTotalPrecio(carritoActualizado.totalPrecio();


        return carritoRepository.save(carrito);

    }

    @Override
    public CarritoCompras obtenerCarritoPorId(String idCarrito) throws Exception {
        // Buscar carrito por ID
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
    }

    @Override
    public List<CarritoCompras> obtenerTodosLosCarritos() throws Exception {
        // Obtener todos los carritos
        return carritoRepository.findAll();
    }

    @Override
    public void añadirEventoCarrito(String idEvento,String idCliente) throws Exception {
// Verificar si el evento existe
        Optional<Evento> evento = eventoRepository.findById(idEvento);

        if (evento.isPresent()) {
            // Por simplicidad, asumimos que ya tienes el carrito del usuario (ej. usuario logueado)

            // Añadir el evento al carrito
            carritoRepository.getItems().add(evento.getId());

            // Guardar el carrito con el evento añadido
            comprasRepository.save(carrito);
        }
    }

    @Override
    public void procesarEvento(DTOEventoCarrito evento) throws Exception {

    }
    /*@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServicioImp implements CarritoServicio {

    private final CarritoComprasRepository comprasRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public String crearCarrito(DTOCrearCarrito carritoDTO) throws Exception {
        // Verificar si el cliente existe
        Cliente cliente = clienteRepository.findById(carritoDTO.idCliente())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        // Crear el carrito
        Carrito carrito = new Carrito();
        carrito.setIdCliente(cliente.getId());
        carrito.setProductos(carritoDTO.productos());
        carrito.setTotal(carritoDTO.total());

        // Guardar el carrito en la base de datos
        Carrito carritoGuardado = comprasRepository.save(carrito);

        return carritoGuardado.getId();
    }

    @Override
    public void eliminarCarrito(String idCarrito) throws Exception {
        // Buscar carrito por ID
        Carrito carrito = comprasRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        // Eliminar el carrito
        comprasRepository.delete(carrito);
    }

    @Override
    public Carrito actualizarCarrito(String idCarrito, DTOActualizarCarrito carritoActualizado) throws Exception {
        // Buscar carrito por ID
        Carrito carrito = comprasRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        // Actualizar los detalles del carrito
        carrito.setProductos(carritoActualizado.productos());
        carrito.setTotal(carritoActualizado.total());

        // Guardar los cambios
        return comprasRepository.save(carrito);
    }

    @Override
    public Carrito obtenerCarritoPorId(String idCarrito) throws Exception {
        // Buscar carrito por ID
        return comprasRepository.findById(idCarrito)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
    }

    @Override
    public List<Carrito> obtenerTodosLosCarritos() throws Exception {
        // Obtener todos los carritos
        return comprasRepository.findAll();
    }

    @Override
    public void procesarEvento(DTOEventoCarrito evento) throws Exception {
        // Procesar diferentes tipos de eventos
        switch (evento.tipoEvento()) {
            case "CREAR":
                DTOCrearCarrito dtoCrearCarrito = // Lógica para crear un DTO desde el evento
                crearCarrito(dtoCrearCarrito);
                break;

            case "ACTUALIZAR":
                DTOActualizarCarrito dtoActualizarCarrito = // Lógica para crear un DTO desde el evento
                actualizarCarrito(evento.idCarrito(), dtoActualizarCarrito);
                break;

            case "ELIMINAR":
                eliminarCarrito(evento.idCarrito());
                break;

            default:
                throw new Exception("Tipo de evento no soportado: " + evento.tipoEvento());
        }
    }
}
*/

}
