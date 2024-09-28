package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.modelo.enums.RolUsuario;
import co.edu.uniquindio.proyecto.modelo.vo.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)

public class Cuenta extends Usuario {

    @Id
    @EqualsAndHashCode.Include
    private String idUsuario;

    private CarritoCompras carrito;
    private List<Compra> historialCompras;
    private List<Cupon> cupones;
    private List<TicketSoporte> ticketsSoporte;
    private RolUsuario rol;
    private EstadoCuenta estado;
    private Notificacion notificacion;
    private HistorialActividad historialActividad;
    private String email;
    private String contrasena;
}
