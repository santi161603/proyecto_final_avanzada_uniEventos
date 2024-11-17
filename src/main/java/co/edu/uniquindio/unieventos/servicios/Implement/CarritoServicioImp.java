package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.CarritoObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.ItemCarritoDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import co.edu.uniquindio.unieventos.repositorio.CarritoComprasRepository;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import lombok.RequiredArgsConstructor;
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
    public void anadirItem(ItemCarritoDTO items, String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Comprobar que el evento/item existe (opcional, depende de tu lógica)
        Optional<Evento> eventoOptional = eventoRepository.findById(String.valueOf(items.eventoId()));

        if (eventoOptional.isEmpty()) {
            throw new Exception("Item con ID " + items.eventoId() + " no encontrado");
        }

        // Obtener el evento y buscar el subevento dentro del evento
        Evento evento = eventoOptional.get();
        SubEvento subEvento = evento.getSubEvent().stream()
                .filter(sub -> sub.getIdSubEvento() == items.idSubevento())
                .findFirst()
                .orElseThrow(() -> new Exception("Subevento no encontrado"));

        // Calcular el total (cantidadEntradas * precioSubevento)
        double precioSubevento = subEvento.getPrecioEntrada();
        double totalPrecioItem = items.cantidadEntradas() * precioSubevento;

        // Verificar si ya existe el item con el mismo eventoId y subeventoId en el carrito
        Optional<ItemCarritoVO> existingItemOptional = carritoCompras.getItems().stream()
                .filter(item -> item.getEventoId().equals(items.eventoId()) && item.getIdSubevento() == items.idSubevento())
                .findFirst();

        if (existingItemOptional.isPresent()) {
            // Si el item ya existe, sumamos las entradas al item existente
            ItemCarritoVO existingItem = existingItemOptional.get();
            existingItem.setCantidadEntradas(existingItem.getCantidadEntradas() + items.cantidadEntradas());

            // Actualizar el total del carrito
            carritoCompras.setTotalPrecio(carritoCompras.getTotalPrecio() + totalPrecioItem);
        } else {
            // Si el item no existe, crear uno nuevo
            ItemCarritoVO itemCarritoVO = new ItemCarritoVO();
            itemCarritoVO.setEventoId(items.eventoId());
            itemCarritoVO.setIdSubevento(items.idSubevento());
            itemCarritoVO.setCantidadEntradas(items.cantidadEntradas());

            // Añadir el nuevo item al carrito
            carritoCompras.getItems().add(itemCarritoVO);

            // Actualizar el total del carrito con el nuevo item
            if (carritoCompras.getTotalPrecio() == null) {
                carritoCompras.setTotalPrecio(totalPrecioItem);
            } else {
                carritoCompras.setTotalPrecio(carritoCompras.getTotalPrecio() + totalPrecioItem);
            }
        }

        // Guardar el carrito actualizado con los items añadidos
        carritoRepository.save(carritoCompras);
    }




    @Override
    public void eliminarItem(ItemCarritoDTO items, String usuarioId) throws Exception {
        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Verificar si el item está vacío
        if (items == null) {
            throw new Exception("El item a eliminar está vacío.");
        }

        // Buscar el ItemCarritoVO correspondiente en el carrito
        Optional<ItemCarritoVO> itemCarritoOptional = carritoCompras.getItems().stream()
                .filter(item -> item.getEventoId().equals(items.eventoId()) && item.getIdSubevento() == items.idSubevento())
                .findFirst();

        if (itemCarritoOptional.isPresent()) {
            // Obtener el item encontrado
            ItemCarritoVO itemCarrito = itemCarritoOptional.get();

            // Obtener el evento correspondiente al ID de evento
            Optional<Evento> eventoOptional = eventoRepository.findById(String.valueOf(itemCarrito.getEventoId()));

            if (eventoOptional.isEmpty()) {
                throw new Exception("Evento con ID " + itemCarrito.getEventoId() + " no encontrado.");
            }

            Evento evento = eventoOptional.get();

            // Buscar el subevento correspondiente al ID de subevento
            SubEvento subEventoOptional = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == items.idSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            if (subEventoOptional == null) {
                throw new Exception("Subevento con ID " + itemCarrito.getIdSubevento() + " no encontrado.");
            }


            // Obtener el precio del subevento y calcular el total a restar
            double precioSubtotal = subEventoOptional.getPrecioEntrada() * itemCarrito.getCantidadEntradas();

            // Restar el precio del item del total del carrito
            carritoCompras.setTotalPrecio(carritoCompras.getTotalPrecio() - precioSubtotal);

            // Eliminar el item del carrito
            carritoCompras.getItems().remove(itemCarrito);

            // Guardar el carrito actualizado
            carritoRepository.save(carritoCompras);
        } else {
            throw new Exception("Item con ID de evento " + items.eventoId() + " y ID de subevento " + items.idSubevento() + " no encontrado en el carrito.");
        }
    }


    @Override
    public List<ItemCarritoVO> obtenerListaItems(String usuarioId) throws Exception {

        // Buscar el carrito del cliente por su ID de usuario
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Obtener la lista de items (IDs de los eventos) en el carrito
        List<ItemCarritoVO> items = carritoCompras.getItems();

        // Si el carrito está vacío, devolver una lista vacía o manejar como sea necesario
        if (items.isEmpty()) {
            throw new Exception("El carrito está vacío para el usuario: " + usuarioId);
        }

        // Devolver la lista de items
        return items;
    }

    @Override
    public void aumentarCantidad(ItemCarritoDTO items, String usuarioId) throws Exception {
        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Verificar si el item está vacío
        if (items == null) {
            throw new Exception("El item a modificar está vacío.");
        }

        // Buscar el ItemCarritoVO correspondiente en el carrito
        Optional<ItemCarritoVO> itemCarritoOptional = carritoCompras.getItems().stream()
                .filter(item -> item.getEventoId().equals(items.eventoId()) && item.getIdSubevento() == items.idSubevento())
                .findFirst();

        if (itemCarritoOptional.isPresent()) {
            // Obtener el item encontrado
            ItemCarritoVO itemCarrito = itemCarritoOptional.get();

            // Obtener el evento correspondiente al ID de evento
            Optional<Evento> eventoOptional = eventoRepository.findById(String.valueOf(itemCarrito.getEventoId()));

            if (eventoOptional.isEmpty()) {
                throw new Exception("Evento con ID " + itemCarrito.getEventoId() + " no encontrado.");
            }

            Evento evento = eventoOptional.get();

            // Buscar el subevento correspondiente al ID de subevento
            SubEvento subEventoOptional = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == items.idSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            if (subEventoOptional == null) {
                throw new Exception("Subevento con ID " + itemCarrito.getIdSubevento() + " no encontrado.");
            }

            // Verificar si la cantidad de entradas no supera el límite del subevento
            if (itemCarrito.getCantidadEntradas() < subEventoOptional.getCantidadEntradas()) {
                itemCarrito.setCantidadEntradas(itemCarrito.getCantidadEntradas() + 1);

                // Calcular el nuevo precio y actualizar el total del carrito
                double precioSubtotal = subEventoOptional.getPrecioEntrada() * itemCarrito.getCantidadEntradas();
                carritoCompras.setTotalPrecio(carritoCompras.getTotalPrecio() + subEventoOptional.getPrecioEntrada());

                // Guardar el carrito actualizado
                carritoRepository.save(carritoCompras);
            } else {
                throw new Exception("No se puede aumentar la cantidad de entradas más allá de la cantidad disponible en el subevento.");
            }
        } else {
            throw new Exception("Item con ID de evento " + items.eventoId() + " y ID de subevento " + items.idSubevento() + " no encontrado en el carrito.");
        }
    }

    @Override
    public void reducirCantidad(ItemCarritoDTO items, String usuarioId) throws Exception {
        // Buscar el carrito del cliente por su ID
        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        // Verificar si el carrito del usuario fue encontrado
        if (carritoCompras == null || carritoCompras.getIdCarritoCompras().isEmpty()) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        // Verificar si el item está vacío
        if (items == null) {
            throw new Exception("El item a modificar está vacío.");
        }

        // Buscar el ItemCarritoVO correspondiente en el carrito
        Optional<ItemCarritoVO> itemCarritoOptional = carritoCompras.getItems().stream()
                .filter(item -> item.getEventoId().equals(items.eventoId()) && item.getIdSubevento() == items.idSubevento())
                .findFirst();

        if (itemCarritoOptional.isPresent()) {
            // Obtener el item encontrado
            ItemCarritoVO itemCarrito = itemCarritoOptional.get();

            // Obtener el evento correspondiente al ID de evento
            Optional<Evento> eventoOptional = eventoRepository.findById(String.valueOf(itemCarrito.getEventoId()));

            if (eventoOptional.isEmpty()) {
                throw new Exception("Evento con ID " + itemCarrito.getEventoId() + " no encontrado.");
            }

            Evento evento = eventoOptional.get();

            // Buscar el subevento correspondiente al ID de subevento
            SubEvento subEventoOptional = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == items.idSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            if (subEventoOptional == null) {
                throw new Exception("Subevento con ID " + itemCarrito.getIdSubevento() + " no encontrado.");
            }

            // Verificar que la cantidad no sea menor a 1
            if (itemCarrito.getCantidadEntradas() > 1) {
                itemCarrito.setCantidadEntradas(itemCarrito.getCantidadEntradas() - 1);

                // Calcular el nuevo precio y actualizar el total del carrito
                double precioSubtotal = subEventoOptional.getPrecioEntrada() * itemCarrito.getCantidadEntradas();
                carritoCompras.setTotalPrecio(carritoCompras.getTotalPrecio() - subEventoOptional.getPrecioEntrada());

                // Guardar el carrito actualizado
                carritoRepository.save(carritoCompras);
            } else {
                throw new Exception("La cantidad de entradas no puede ser menor a 1.");
            }
        } else {
            throw new Exception("Item con ID de evento " + items.eventoId() + " y ID de subevento " + items.idSubevento() + " no encontrado en el carrito.");
        }
    }


    @Override
    public CarritoObtenidoDTO obtenerCarrito(String usuarioId) throws Exception {

        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        if(carritoCompras == null) {
            throw new Exception("Carrito no encontrado para el usuario: " + usuarioId);
        }

        return mapearCarritoUsuario(carritoCompras);
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
        carritoCompras.setTotalPrecio(0.0);

        // Guardar el carrito actualizado (vacío)
        carritoRepository.save(carritoCompras);
    }

    @Override
    public void actualizarItemCarrito(ItemCarritoDTO items, String usuarioId) throws Exception {

        CarritoCompras carritoCompras = carritoRepository.findByUsuarioId(usuarioId);

        if(carritoCompras == null){
            throw new Exception("Carrito de usuario no encontrado");
        }

        // Buscar el item en el carrito con el mismo eventoId e idSubevento
        Optional<ItemCarritoVO> itemExistente = carritoCompras.getItems().stream()
                .filter(item -> item.getEventoId().equals(items.eventoId()) &&
                        item.getIdSubevento() == items.idSubevento())
                .findFirst();

        // Validar si el item existe en el carrito
        if (!itemExistente.isPresent()) {
            throw new Exception("Item no encontrado en el carrito");
        }

        // Asignar el cupón al item encontrado
        itemExistente.get().setCupon(items.cupon());

        // Guardar los cambios en el carrito
        carritoRepository.save(carritoCompras);

    }


    private CarritoObtenidoDTO mapearCarritoUsuario(CarritoCompras carrito){
       return new CarritoObtenidoDTO(
               carrito.getIdCarritoCompras(),
               carrito.getUsuarioId(),
               carrito.getItems(),
               carrito.getTotalPrecio()
        );
    }


}
