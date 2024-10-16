package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.DTOActualizarEvento;
import co.edu.uniquindio.unieventos.dto.DTOCrearEvento;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;

import java.util.List;

public interface EventoServicio {

    // Método para crear un evento
    String crearEvento(DTOCrearEvento evento) throws Exception;

    // Método para actualizar un evento
    String actualizarEvento(String idEvento, DTOActualizarEvento eventoActualizado) throws Exception;

    // Método para eliminar un evento por su ID
    void eliminarEvento(String idEvento) throws Exception;

    // Método para obtener un evento por su ID
    Evento obtenerEventoPorId(String idEvento) throws Exception;

    // Método para obtener todos los eventos
    List<Evento> obtenerTodosLosEventos() throws Exception;

    // Método para obtener un evento por categoria
    List<Evento> obtenerEventoCategoria(TipoEvento evento) throws Exception;
}

