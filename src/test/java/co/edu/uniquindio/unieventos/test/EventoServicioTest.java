package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.dto.DTOActualizarEvento;
import co.edu.uniquindio.unieventos.dto.DTOCrearEvento;
import co.edu.uniquindio.unieventos.dto.DTOSubEventos;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventoServicioTest {


}
