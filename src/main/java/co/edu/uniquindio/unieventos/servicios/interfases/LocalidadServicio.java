package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.DTOActualizarLocalidad;
import co.edu.uniquindio.unieventos.dto.DTOCrearLocalidad;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;

import java.util.List;

public interface LocalidadServicio {

    void crearLocalidad(DTOCrearLocalidad localidad) throws Exception;

    List<LocalidadEvento> obtenerLocalidades() throws Exception;

    void actualizarLocalidad(DTOActualizarLocalidad localidad, String localidadId) throws Exception;

    void eliminarLocalidad(String localidadId) throws Exception;

    LocalidadEvento obtenerLocalidadPorId(String localidadId) throws Exception;

    List<LocalidadEvento> obtenerLocalidadesPorCiudad(Ciudades ciudad) throws Exception;

}
