package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.dto.DTOActualizarEvento;
import co.edu.uniquindio.unieventos.dto.DTOCrearEvento;
import co.edu.uniquindio.unieventos.dto.DTOSubEventos;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.servicios.Implement.EventoServicioImp;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventoServicioTest {

    @Autowired
    private EventoServicio eventoServicio;

    @Test
    public void crearEventoTest() throws Exception {
        // Crear un archivo MultipartFile simulado (poster de imagen)
        File file = new File("src/main/resources/Image/pexels-apasaric-2411688.jpg"); // Cambia el nombre del archivo según sea necesario
        byte[] content = Files.readAllBytes(file.toPath());

        // Crear MultipartFile usando la imagen de tu PC
        MultipartFile imagenPoster = new MockMultipartFile("poster", file.getName(), "image/jpeg", content);

        // Crear subeventos de prueba
        List<DTOSubEventos> subEventos = Arrays.asList(
                //los sub eventos tienen la hora del evento, el id de la localidad y la cantidad de entradas
                new DTOSubEventos(LocalDateTime.now(), "094ut09j3ifj290", 50),
                new DTOSubEventos(LocalDateTime.now().plusDays(1), "eoihfwijfojeok", 100)
        );

        // Crear DTO del evento
        DTOCrearEvento dtoEvento = new DTOCrearEvento(
                "Concierto de Rock", // nombre
                Ciudades.BOGOTA,            // ciudad
                "Gran concierto de rock en Bogotá", // descripción
                TipoEvento.CONCIERTO, // tipo de evento// el poster
                subEventos            // lista de subeventos
        );

        // Ejecutar el método crearEvento
        String idEvento = eventoServicio.crearEvento(dtoEvento);

        // Verificar que se haya creado correctamente el evento
        assertNotNull(idEvento, "El ID del evento no debe ser nulo");
    }

    @Test
    public void actualizarEventoTest() throws Exception {

        // Crear un archivo MultipartFile simulado (poster de imagen)
        File file = new File("src/main/resources/Image/mD.jpg"); // Cambia el nombre del archivo según sea necesario
        byte[] content = Files.readAllBytes(file.toPath());

        // Crear MultipartFile usando la imagen de tu PC
        MultipartFile imagenPoster = new MockMultipartFile("poster", file.getName(), "image/jpeg", content);

        // Crear subeventos de prueba actualizados
        List<DTOSubEventos> subEventosActualizados = Arrays.asList(
                new DTOSubEventos(LocalDateTime.now(), "oeifw09ur02924", 75),
                new DTOSubEventos(LocalDateTime.now().plusDays(2), "wopuf092fio2f", 150)
        );

        // Crear DTO del evento a actualizar
        DTOActualizarEvento dtoActualizarEvento = new DTOActualizarEvento(
                "Concierto de Rap",            // nombre actualizado
                "Un increíble concierto de Rap", // descripción actualizada
                imagenPoster,                  // nueva imagen del póster
                subEventosActualizados         // lista actualizada de subeventos
        );

        // Obtener un ID de evento previamente creado
        String idEvento = "6708a0ebe9525f774667f2a9"; // Asegúrate de tener un ID de evento válido para este test

        eventoServicio.actualizarEvento(idEvento, dtoActualizarEvento);

    }

    @Test
    public void eliminarEventoTest() throws Exception {
        eventoServicio.eliminarEvento("6708885ceb87717e585a0b40");
    }


}
