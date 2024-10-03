package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import co.edu.uniquindio.unieventos.modelo.vo.*;
import lombok.*;
import org.bson.types.ObjectId;
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

    private ObjectId carrito;
    private List<Compra> historialCompras;
    private List<Cupon> cupones;
    private Usuario usuario;
    private List<TicketSoporte> ticketsSoporte;
    private RolUsuario rol;
    private EstadoCuenta estado;
    private String email;
    private String contrasena;
}
