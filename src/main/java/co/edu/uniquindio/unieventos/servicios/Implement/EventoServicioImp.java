package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // Mapear la lista de subeventos
        List<SubEvento> subEventos = new ArrayList<>();
            // Usar un bucle for para mapear y guardar los subeventos
            for (DTOSubEventos subeventoDTO : evento.subEventos()) {
                SubEvento subevento = new SubEvento();
                subevento.setFechaEvento(subeventoDTO.fechaEvento());
                subevento.setLocalidades(subeventoDTO.localidades());
                subevento.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                subEventos.add(subevento); // Agregar el subevento a la lista
            }
            // Agregar la lista de subeventos al evento
        nuevoEvento.setSubEvent(subEventos);

        //crear imagen de perfil
        // Ruta del archivo de imagen local
        String filePath = "src/main/resources/Image/evento.jpg";

        // Cargar el archivo de imagen
        File imageFile = new File(filePath);
        String fileName = imageFile.getName(); // Obtener el nombre del archivo
        String contentType = Files.probeContentType(imageFile.toPath()); // Detectar el tipo de contenido

        // Crear el MultipartFile implementando la interfaz manualmente
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return imageFile.length() == 0;
            }

            @Override
            public long getSize() {
                return imageFile.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return Files.readAllBytes(imageFile.toPath());
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(imageFile);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(imageFile.toPath(), dest.toPath());
            }
        };

        // Llamar al método subirImagen
        String imageUrl = imagenesServicio.subirImagen(multipartFile);

        nuevoEvento.setImagenPoster(imageUrl);

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
            List<SubEvento> subEventosActualizados = new ArrayList<>();

            for (DTOSubEventos subeventoDTO : evento.subEventos()) {
                Optional<SubEvento> subeventoExistenteOptional = eventoRepository.findBySubEventoFecha(subeventoDTO.fechaEvento());

                if (subeventoExistenteOptional.isPresent()) {
                    // Si el subevento con la misma fecha ya existe, actualizarlo
                    SubEvento subeventoExistente = subeventoExistenteOptional.get();
                    subeventoExistente.setLocalidades(subeventoDTO.localidades());
                    subeventoExistente.setCantidadEntradas(subeventoDTO.cantidadEntradas());
                    subEventosActualizados.add(subeventoExistente); // Agregar el subevento actualizado a la lista
                } else {
                    // Si no existe un subevento con esa fecha, crear uno nuevo
                    SubEvento nuevoSubEvento = new SubEvento();
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
    public List<EventoObtenidoDTO> obtenerEventoCategoria(TipoEvento tipoEvento) throws Exception {

        List<Evento> eventos = eventoRepository.findByCategoria(tipoEvento);

        return eventos.stream()
                .map(this::mapearAEventoObtenidoDTO)
                .collect(Collectors.toList());
    }

    private EventoObtenidoDTO mapearAEventoObtenidoDTO(Evento evento) {

        return new EventoObtenidoDTO(
                evento.getNombre(),
                evento.getCiudad(),
                evento.getDescripcion(),
                evento.getTipoEvento(),
                mapearListaDeSubEventos(evento.getSubEvent()), // Ahora pasas la lista correctamente mapeada
                evento.getImagenPoster()
                );}

    private List<DTOSubEventos> mapearListaDeSubEventos(List<SubEvento> subEventos) {
        return subEventos.stream()
                .map(this::mapearASubEventoDTO) // Mapea cada subEvento a DTOSubEventos
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

        // Método para mapear subEvento a DTOSubEventos
        private DTOSubEventos mapearASubEventoDTO(SubEvento subEvento) {
            return new DTOSubEventos(
                    subEvento.getFechaEvento(), // Ajusta según los campos reales
                    subEvento.getLocalidades(),
                    subEvento.getCantidadEntradas()// Ajusta según los campos reales
                    // Continúa mapeando los demás campos necesarios
            );

    }
}
