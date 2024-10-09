package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.repositorio.CarritoComprasRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CarritoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServicioImp implements CarritoServicio {

    private final CarritoComprasRepository comprasRepository;
    
    @Override
    public String crearCarrito(DTOCrearCarrito carrito) throws Exception {
        return "";
    }

    @Override
    public void eliminarCarrito(String idCarrito) throws Exception {

    }

    @Override
    public Carrito actualizarCarrito(String idCarrito, DTOActualizarCarrito carritoActualizado) throws Exception {
        return null;
    }

    @Override
    public Carrito obtenerCarritoPorId(String idCarrito) throws Exception {
        return null;
    }

    @Override
    public List<Carrito> obtenerTodosLosCarritos() throws Exception {
        return null;
    }

    @Override
    public void procesarEvento(DTOEventoCarrito evento) throws Exception {

    }
}
