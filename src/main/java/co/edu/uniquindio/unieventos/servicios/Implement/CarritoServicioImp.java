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
public class CarritoServicioImp {
/*
    private final CarritoComprasRepository comprasRepository;
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
    public void añadirEventoCarrito(String idEvento, String idCliente) throws Exception {

        Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);

        if (eventoOptional.isEmpty()) {
            throw new Exception("Evento no encontrado");
        }

        // Buscar el carrito del cliente por su id
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(new ObjectId(idCliente));

        if (carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + idCliente);
        }

        // Añadir el evento al carrito (suponiendo que items es una lista de IDs de eventos u objetos)
        carritoCompras.getItems().add(new ObjectId(idEvento));

        // Guardar el carrito actualizado con el evento añadido
        carritoRepository.save(carritoCompras);
    }

    @Override
    public void eliminarEventoCarrito(String idEvento, String idCliente) throws Exception {Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);

        if (eventoOptional.isEmpty()) {
            throw new Exception("Evento no encontrado");
        }

        // Buscar el carrito del cliente por su id
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(new ObjectId(idCliente));

        if (carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + idCliente);
        }

        // Añadir el evento al carrito (suponiendo que items es una lista de IDs de eventos u objetos)
        carritoCompras.getItems().add(new ObjectId(idEvento));

        // Guardar el carrito actualizado con el evento añadido
        carritoRepository.save(carritoCompras);
    }

    /*
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
