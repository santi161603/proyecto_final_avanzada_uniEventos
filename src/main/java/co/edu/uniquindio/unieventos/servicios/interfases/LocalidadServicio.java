package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.DTOActualizarLocalidad;
import co.edu.uniquindio.unieventos.dto.DTOCrearLocalidad;
import co.edu.uniquindio.unieventos.dto.LocalidadEventoObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.NombreyIdLocalidadObtenidaDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;

import java.util.List;

public interface LocalidadServicio {

    void crearLocalidad(DTOCrearLocalidad localidad) throws Exception;

    List<LocalidadEventoObtenidoDTO> obtenerLocalidades() throws Exception;

    void actualizarLocalidad(DTOActualizarLocalidad localidad, String idLocalidad) throws Exception;

    void eliminarLocalidad(String localidadId) throws Exception;

    LocalidadEventoObtenidoDTO obtenerLocalidadPorId(String idLocalidad) throws Exception;

    List<LocalidadEventoObtenidoDTO> obtenerLocalidadesPorCiudad(Ciudades ciudad) throws Exception;

    List<NombreyIdLocalidadObtenidaDTO> obtenerTodasLasLocalidadesNombreID() throws Exception;

}
