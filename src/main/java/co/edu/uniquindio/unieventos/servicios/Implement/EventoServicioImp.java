package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarEvento;
import co.edu.uniquindio.unieventos.dto.DTOCrearEvento;
import co.edu.uniquindio.unieventos.dto.DTOSubEventos;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.subEvento;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServicioImp implements EventoServicio{

    private final EventoRepository eventoRepository;
    private final ImagenesServicio imagenesServicio;

    @Override
    public String crearEvento(DTOCrearEvento evento) throws Exception {
// Mapear DTO a entidad Evento
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(evento.nombre());
        nuevoEvento.setCiudad(evento.ciudad());
        nuevoEvento.setDescripcion(evento.descripcion());
        nuevoEvento.setTipoEvento(evento.tipoEvento());

        if(evento.imagenPoster() != null) {
            String urlImagen = imagenesServicio.subirImagen(evento.imagenPoster());
            nuevoEvento.setImagenPoster(urlImagen);
        }

        // Mapear la lista de subeventos
        List<subEvento> subEventos = new ArrayList<>();
            // Usar un bucle for para mapear y guardar los subeventos
            for (DTOSubEventos subeventoDTO : evento.subEventos()) {
                subEvento subevento = new subEvento();
                subevento.setFechaEvento(subeventoDTO.fechaEvento());
                subevento.setLocalidades(subeventoDTO.localidades());
                subevento.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                subEventos.add(subevento); // Agregar el subevento a la lista
            }
            // Agregar la lista de subeventos al evento
        nuevoEvento.setSubEvent(subEventos);

            eventoRepository.save(nuevoEvento);

        // Persistir el evento en la base de datos
        return nuevoEvento.getIdEvento();
    }
    @Override
    public String actualizarEvento(String idEvento, DTOActualizarEvento evento) throws Exception {
        // Buscar el evento existente por su ID
        Optional<Evento> eventoExistente = eventoRepository.findById(idEvento);

        if (eventoExistente.isEmpty()) {
            throw new Exception("El evento con el ID proporcionado no existe");
        }

        // Obtener el evento de la base de datos
        Evento eventoActualizado = eventoExistente.get();

        // Actualizar el nombre si se proporciona
        if (evento.nombre() != null && !evento.nombre().isEmpty()) {
            eventoActualizado.setNombre(evento.nombre());
        }

        // Actualizar la descripción si se proporciona
        if (evento.descripcion() != null && !evento.descripcion().isEmpty()) {
            eventoActualizado.setDescripcion(evento.descripcion());
        }

        // Si se proporciona una nueva imagen, actualizar la imagen del póster
        if (evento.imagenPoster() != null) {
            // Obtener la URL de la imagen actual del evento
            String urlImagenActual = eventoActualizado.getImagenPoster();

            // Llamar al servicio de imágenes para actualizar la imagen
            String nuevaUrlImagen = imagenesServicio.ActualizarImagen(urlImagenActual, evento.imagenPoster());

            // Asignar la nueva URL de la imagen al evento
            eventoActualizado.setImagenPoster(nuevaUrlImagen);
        }

        // Actualizar la lista de subeventos si se proporciona
        if (evento.subEventos() != null && !evento.subEventos().isEmpty()) {
            List<subEvento> subEventosActualizados = new ArrayList<>();

            for (DTOSubEventos subeventoDTO : evento.subEventos()) {
                Optional<subEvento> subeventoExistenteOptional = eventoRepository.findBySubEventoFecha(subeventoDTO.fechaEvento());

                if (subeventoExistenteOptional.isPresent()) {
                    // Si el subevento con la misma fecha ya existe, actualizarlo
                    subEvento subeventoExistente = subeventoExistenteOptional.get();
                    subeventoExistente.setLocalidades(subeventoDTO.localidades());
                    subeventoExistente.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                    subEventosActualizados.add(subeventoExistente); // Agregar el subevento actualizado a la lista
                } else {
                    // Si no existe un subevento con esa fecha, crear uno nuevo
                    subEvento nuevoSubEvento = new subEvento();
                    nuevoSubEvento.setFechaEvento(subeventoDTO.fechaEvento());
                    nuevoSubEvento.setLocalidades(subeventoDTO.localidades());
                    nuevoSubEvento.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                    subEventosActualizados.add(nuevoSubEvento); // Agregar el nuevo subevento a la lista
                }
            }

            // Reemplazar los subeventos existentes del evento con los nuevos o actualizados
            eventoActualizado.setSubEvent(subEventosActualizados);
        }

        // Guardar los cambios en la base de datos
        eventoRepository.save(eventoActualizado);

        return idEvento;
    }


    @Override
    public void eliminarEvento(String idEvento) throws Exception {
        // Verificar si el evento existe en la base de datos
        Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);

        // Si el evento no existe, lanzar una excepción
        if (!eventoOptional.isPresent()) {
            throw new Exception("El evento con ID " + idEvento + " no existe.");
        }

        String urlIm = eventoOptional.get().getImagenPoster();
        // Si el evento existe, eliminarlo
        imagenesServicio.eliminarImagen(urlIm);
        eventoRepository.delete(eventoOptional.get());
    }

    @Override
    public Evento obtenerEventoPorId(String idEvento) throws Exception {
        return null;
    }

    @Override
    public List<Evento> obtenerTodosLosEventos() throws Exception {
        return eventoRepository.findAll();
    }

    @Override
    public List<Evento> obtenerEventoCategoria(TipoEvento tipoEvento) throws Exception {
        return eventoRepository.findByCategoria(tipoEvento);
    }
}
