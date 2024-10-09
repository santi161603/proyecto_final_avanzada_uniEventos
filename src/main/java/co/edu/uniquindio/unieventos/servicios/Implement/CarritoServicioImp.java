package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.repositorio.CarritoComprasRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServicioImp implements CarritoServicio {

    private final CarritoComprasRepository comprasRepository;
    
    @Override
    public String crearCarrito(DTOCrearCarrito carrito) throws Exception {
        return "";
    }

    @Override
    public void eliminarCarrito(String idCarrito) throws Exception {

    }

    @Override
    public Carrito actualizarCarrito(String idCarrito, DTOActualizarCarrito carritoActualizado) throws Exception {
        return null;
    }

    @Override
    public Carrito obtenerCarritoPorId(String idCarrito) throws Exception {
        return null;
    }

    @Override
    public List<Carrito> obtenerTodosLosCarritos() throws Exception {
        return null;
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
