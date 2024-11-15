package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.repositorio.LocalidadRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServicioImp implements EventoServicio{

    private final EventoRepository eventoRepository;
    private final LocalidadRepository localidadRepository;
    private final ImagenesServicio imagenesServicio;

    @Override
    public String crearEvento(DTOCrearEvento evento) throws Exception {
// Mapear DTO a entidad Evento
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(evento.nombre());
        nuevoEvento.setEstadoEvento(EstadoCuenta.ACTIVO);
        nuevoEvento.setDescripcion(evento.descripcion());
        nuevoEvento.setTipoEvento(evento.tipoEvento());

        // Mapear la lista de subeventos
        List<SubEvento> subEventos = new ArrayList<>();
            // Usar un bucle for para mapear y guardar los subeventos
        for (int i = 0; i < evento.subEventos().size(); i++) {
            DTOSubEventos subeventoDTO = evento.subEventos().get(i); // Obtener el DTO en la posición i
            SubEvento subevento = new SubEvento();
            subevento.setIdSubEvento(i);  // Asignar el índice i como el ID del subevento
            subevento.setFechaEvento(subeventoDTO.fechaEvento());
            subevento.setLocalidad(subeventoDTO.localidad());
            subevento.setHoraEvento(subeventoDTO.horaEvento());
            subevento.setCantidadEntradas(subeventoDTO.cantidadEntradas());
            subevento.setPrecioEntrada(subeventoDTO.precioEntrada());
            subevento.setEstadoSubevento(EstadoCuenta.ACTIVO);
            subEventos.add(subevento); // Agregar el subevento a la lista
        }
            // Agregar la lista de subeventos al evento
        nuevoEvento.setSubEvent(subEventos);

        if(evento.imageEvento() == null) {
            nuevoEvento.setImagenPoster("https://firebasestorage.googleapis.com/v0/b/unieventos-d397d.appspot.com/o/f8cca7dd-395f-40c8-ae39-6855add8dc0d-evento.jpg?alt=media&token=bb1dcccc-fa10-4a87-a0b3-c6ba1773e599");
        }else {
            nuevoEvento.setImagenPoster(evento.imageEvento());
        }
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
        if (evento.imagenPoster() != null && !evento.imagenPoster().equals(eventoActualizado.getImagenPoster())) {
            if(eventoActualizado.getImagenPoster().equals("https://firebasestorage.googleapis.com/v0/b/unieventos-d397d.appspot.com/o/f8cca7dd-395f-40c8-ae39-6855add8dc0d-evento.jpg?alt=media&token=bb1dcccc-fa10-4a87-a0b3-c6ba1773e599")) {
                eventoActualizado.setImagenPoster(evento.imagenPoster());
            }else {
                // Obtener la URL de la imagen actual del evento
                imagenesServicio.eliminarImagen(eventoActualizado.getImagenPoster());
                eventoActualizado.setImagenPoster(evento.imagenPoster());
            }
        }

        if(evento.tipoEvento() != null && !evento.tipoEvento().equals(eventoActualizado.getTipoEvento())){
            eventoActualizado.setTipoEvento(evento.tipoEvento());
        }
        if(evento.estadoEvento() != null && !evento.estadoEvento().equals(eventoActualizado.getEstadoEvento())) {
            eventoActualizado.setEstadoEvento(evento.estadoEvento());
        }

        // Actualizar o agregar subeventos
        if (evento.subEventos() != null) {
            List<SubEvento> subEventosActualizados = eventoActualizado.getSubEvent();

            for (SubEventoObtenidoDto subeventoDTO : evento.subEventos()) {
                if (subeventoDTO.idSubEvento() != 0) {
                    // Actualizar subevento existente
                    SubEvento subEventoExistente = subEventosActualizados.stream()
                            .filter(s -> s.getIdSubEvento() == subeventoDTO.idSubEvento())
                            .findFirst()
                            .orElse(null);

                    if (subEventoExistente != null) {
                        // Actualizar los campos del subevento existente
                        subEventoExistente.setFechaEvento(subeventoDTO.fechaEvento());
                        subEventoExistente.setLocalidad(subeventoDTO.localidad());
                        subEventoExistente.setHoraEvento(subeventoDTO.horaEvento());
                        subEventoExistente.setEstadoSubevento(subeventoDTO.estadoSubEvento());
                        subEventoExistente.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                        subEventoExistente.setPrecioEntrada(subeventoDTO.precioEntrada());
                    }
                } else {
                    // Crear un nuevo subevento y asignar un ID único
                    SubEvento subevento = new SubEvento();
                    int nuevoId = obtenerSiguienteIdUnico(subEventosActualizados);
                    subevento.setIdSubEvento(nuevoId);
                    subevento.setFechaEvento(subeventoDTO.fechaEvento());
                    subevento.setLocalidad(subeventoDTO.localidad());
                    subevento.setHoraEvento(subeventoDTO.horaEvento());
                    subevento.setEstadoSubevento(subeventoDTO.estadoSubEvento());
                    subevento.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                    subevento.setPrecioEntrada(subeventoDTO.precioEntrada());

                    // Agregar el nuevo subevento a la lista
                    subEventosActualizados.add(subevento);
                }
            }

            // Actualizar la lista de subeventos en el evento
            eventoActualizado.setSubEvent(subEventosActualizados);
        }

        // Guardar los cambios en la base de datos
        eventoRepository.save(eventoActualizado);

        return idEvento;
    }

    // Métdo auxiliar para obtener un ID único para nuevos subeventos
    private int obtenerSiguienteIdUnico(List<SubEvento> subEventosActuales) {
        return subEventosActuales.stream()
                .mapToInt(SubEvento::getIdSubEvento)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void eliminarEvento(String idEvento) throws Exception {
        // Verificar si el evento existe en la base de datos
        Optional<Evento> eventoOptional = eventoRepository.findById(idEvento);

        // Si el evento no existe, lanzar una excepción
        if (eventoOptional.isEmpty()) {
            throw new Exception("El evento con ID " + idEvento + " no existe.");
        }
        eventoOptional.get().setEstadoEvento(EstadoCuenta.ELIMINADO);
        eventoRepository.save(eventoOptional.get());
    }

    @Override
    public EventoObtenidoDTO obtenerEventoPorId(String idEvento) throws Exception {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new Exception("Evento no encontrado con ID: " + idEvento));

            return mapearAEventoObtenidoDTO(evento);

    }

    @Override
    public List<EventoObtenidoDTO> obtenerTodosLosEventos() throws Exception {

        List<Evento> evento = eventoRepository.findAll();
        return evento.stream()
                .map(this::mapearAEventoObtenidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoObtenidoDTO> obtenerEventoCategoria(TipoEventoDTO tipoEvento) throws Exception {

        List<Evento> eventos = eventoRepository.findByCategoria(tipoEvento.tipoEvento());

        return eventos.stream()
                .map(this::mapearAEventoObtenidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoObtenidoDTO> obtenerEventosPorCiudad(Ciudades ciudad) throws Exception {
        // 1. Obtener las localidades en la ciudad especificada
        List<LocalidadEvento> localidades = localidadRepository.findByCity(ciudad);

        // 2. Extraer los IDs de esas localidades
        List<String> localidadesIds = localidades.stream()
                .map(LocalidadEvento::getIdLocalidad)
                .collect(Collectors.toList());

        // 3. Buscar los eventos que tengan subeventos en esas localidades
        List<Evento> eventoList = eventoRepository.findBySubEventoLocalidadIn(localidadesIds);

        return eventoList.stream()
                .map(this::mapearAEventoObtenidoDTO)
                .collect(Collectors.toList());
    }

    private EventoObtenidoDTO mapearAEventoObtenidoDTO(Evento evento) {

        return new EventoObtenidoDTO(
                evento.getIdEvento(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getEstadoEvento(),
                evento.getTipoEvento(),
                mapearListaDeSubEventosObtenidos(evento.getSubEvent()), // Ahora pasas la lista correctamente mapeada
                evento.getImagenPoster()
                );}

    private List<SubEventoObtenidoDto> mapearListaDeSubEventos(List<SubEvento> subEventos) {
        return subEventos.stream()
                .map(this::mapearASubEventoDTO) // Mapea cada subEvento a DTOSubEventos
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

        // Méto para mapear subEvento a DTOSubEventos
        private SubEventoObtenidoDto mapearASubEventoDTO(SubEvento subEvento) {
            return new SubEventoObtenidoDto(
                    subEvento.getIdSubEvento(),
                    subEvento.getFechaEvento(), // Ajusta según los campos reales
                    subEvento.getLocalidad(),
                    subEvento.getHoraEvento(),
                    subEvento.getCantidadEntradas(),// Ajusta según los campos reales
                    subEvento.getPrecioEntrada(),
                    subEvento.getEstadoSubevento()
                    // Continúa mapeando los demás campos necesarios
            );

    }

    private List<SubEventoObtenidoDto> mapearListaDeSubEventosObtenidos(List<SubEvento> subEventos) {
        return subEventos.stream()
                .map(this::mapearASubEventoObtenidosDTO) // Mapea cada subEvento a DTOSubEventos
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

    // Méto para mapear subEvento a DTOSubEventos
    private SubEventoObtenidoDto mapearASubEventoObtenidosDTO(SubEvento subEvento) {
        return new SubEventoObtenidoDto(
                subEvento.getIdSubEvento(),
                subEvento.getFechaEvento(), // Ajusta según los campos reales
                subEvento.getLocalidad(),
                subEvento.getHoraEvento(),
                subEvento.getCantidadEntradas(),// Ajusta según los campos reales
                subEvento.getPrecioEntrada(),
                subEvento.getEstadoSubevento()
                // Continúa mapeando los demás campos necesarios
        );

    }

    @Override
    public void reducirCantidadEntradasSubEvento(ItemCarritoVO item) throws Exception{

        Evento evento = eventoRepository.findById(item.getEventoId()).orElseThrow(() -> new Exception("no se encontro el evetno con ID: " + item.getEventoId()));

        SubEvento subEventoExistente = evento.getSubEvent().stream()
                .filter(s -> s.getIdSubEvento() == item.getIdSubevento())
                .findFirst()
                .orElse(null);

        int cantidadActual = subEventoExistente.getCantidadEntradas();

        subEventoExistente.setCantidadEntradas(cantidadActual - item.getCantidadEntradas());

        eventoRepository.save(evento);
    }
}
