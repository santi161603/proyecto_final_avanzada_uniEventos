package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.CuponActualizadoDTO;
import co.edu.uniquindio.unieventos.dto.CuponObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.DTOCrearCupon;

import java.util.List;

public interface CuponServicio {

    void crearCupon(DTOCrearCupon cuponDTO) throws Exception;

    void eliminarCupon(String idCupon) throws Exception;

    void actualizarCupon(String idCupon, CuponActualizadoDTO cuponActualizado) throws Exception;

    CuponObtenidoDTO obtenerCuponPorId(String idCupon) throws Exception;

    List<CuponObtenidoDTO> obtenerTodosLosCupones() throws Exception;

    void reducirCantidadCupon(String idCupon) throws Exception;

    void aumentarCantidadCupon(String idCupon) throws Exception;

}

