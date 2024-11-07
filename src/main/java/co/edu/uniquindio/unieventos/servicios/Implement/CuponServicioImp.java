package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.CuponActualizadoDTO;
import co.edu.uniquindio.unieventos.dto.CuponObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.DTOCrearCupon;
import co.edu.uniquindio.unieventos.modelo.documentos.Cupon;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCupon;
import co.edu.uniquindio.unieventos.repositorio.CuponRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuponServicio;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CuponServicioImp implements CuponServicio {

    private final CuponRepository cuponRepository;

    @Override
    public void crearCupon(DTOCrearCupon cuponDTO) throws Exception {

        Cupon cupon = new Cupon();
        cupon.setDescripcionCupon(cuponDTO.descripcionCupon());
        cupon.setNombreCupon(cuponDTO.nombreCupon());
        cupon.setPorcentajeDescuento(cuponDTO.porcentajeDescuento());
        cupon.setCantidad(cuponDTO.cantidad());
        cupon.setFechaVencimiento(cuponDTO.fechaVencimiento());
        cupon.setEstadoCupon(EstadoCupon.ACTIVO);

        cuponRepository.save(cupon);
    }

    @Override
    public void eliminarCupon(@NotNull String idCupon) throws Exception {

        Optional<Cupon> cupon = cuponRepository.findById(idCupon);

        if (cupon.isPresent()) {
            Cupon cuponOptenido = cupon.get();

            cuponOptenido.setEstadoCupon(EstadoCupon.ELIMINADO);
            cuponRepository.save(cuponOptenido);
        }else {
            throw new Exception("Cupon inexistente");
        }

    }

    @Override
    public void actualizarCupon(String idCupon, CuponActualizadoDTO cuponActualizado) throws Exception {

        Optional<Cupon> cupon = cuponRepository.findById(idCupon);

        if (cupon.isPresent()) {
            Cupon cuponOptenido = cupon.get();

            if(!cuponActualizado.nombreCupon().equals(cuponOptenido.getNombreCupon())){
                cuponOptenido.setNombreCupon(cuponActualizado.nombreCupon());
            }
            if(!cuponActualizado.descripcionCupon().equals(cuponOptenido.getDescripcionCupon())){
                cuponOptenido.setDescripcionCupon(cuponActualizado.descripcionCupon());
            }
            if(!cuponActualizado.estadoCupon().equals(cuponOptenido.getEstadoCupon())){
                cuponOptenido.setEstadoCupon(cuponActualizado.estadoCupon());
            }
            if(cuponActualizado.porcentajeDescuento() != cuponOptenido.getPorcentajeDescuento()){
                cuponOptenido.setPorcentajeDescuento(cuponActualizado.porcentajeDescuento());
            }
            if(!cuponActualizado.fechaVencimiento().equals(cuponOptenido.getFechaVencimiento())){
                cuponOptenido.setFechaVencimiento(cuponActualizado.fechaVencimiento());
            }
            if (cuponActualizado.cantidad() != cuponOptenido.getCantidad()){
                cuponOptenido.setCantidad(cuponActualizado.cantidad());
            }
            cuponRepository.save(cuponOptenido);
        }
        else {
            throw new Exception("Cupon inexistente");
        }
    }

    @Override
    public CuponObtenidoDTO obtenerCuponPorId(String idCupon) throws Exception {

        Optional<Cupon> cupon = cuponRepository.findById(idCupon);

        if (cupon.isEmpty()){
            throw new Exception("Cupon no encontrado");
        }

        Cupon cuponObtenido = cupon.get();

        return mapearCuponObtenido(cuponObtenido);
    }

    private CuponObtenidoDTO mapearCuponObtenido(Cupon cuponObtenido) {
        return new CuponObtenidoDTO(
                cuponObtenido.getNombreCupon(),
                cuponObtenido.getDescripcionCupon(),
                cuponObtenido.getPorcentajeDescuento(),
                cuponObtenido.getFechaVencimiento()
        );
    }

    @Override
    public List<CuponObtenidoDTO> obtenerTodosLosCupones() throws Exception {

        List<Cupon> cupon = cuponRepository.findAll();

        return cupon.stream()
                .map(this::mapearCuponObtenido)
                .collect(Collectors.toList());
    }

    @Override
    public void reducirCantidadCupon(String idCupon, int cantidadReducir) throws Exception {
        Cupon cupon = cuponRepository.findById(idCupon)
                .orElseThrow(() -> new Exception("Cupon no encontrado"));

        if (cupon.getCantidad() <= 0) {
            throw new Exception("No hay cupones disponibles");
        }

        cupon.setCantidad(cupon.getCantidad() - 1);
        cuponRepository.save(cupon);
    }

    @Override
    public void aumentarCantidadCupon(String idCupon) throws Exception {
        Cupon cupon = cuponRepository.findById(idCupon)
                .orElseThrow(() -> new Exception("Cupon no encontrado"));

        cupon.setCantidad(cupon.getCantidad() + 1);
        cuponRepository.save(cupon);
    }
}
