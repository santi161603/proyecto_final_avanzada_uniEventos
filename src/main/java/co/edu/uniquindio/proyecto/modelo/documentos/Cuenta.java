package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.CarritoCompras;
import co.edu.uniquindio.proyecto.modelo.vo.TicketSoporte;
import co.edu.uniquindio.proyecto.modelo.vo.Usuario;

import java.util.List;

public class Cuenta extends Usuario {
    private CarritoCompras carrito;
    private List<Compra> historialCompras;
    private List<Cupon> cupones;
    private List<TicketSoporte> ticketsSoporte;
}
