package co.edu.uniquindio.unieventos.modelo.documentos;

import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import co.edu.uniquindio.unieventos.modelo.vo.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta {

    @Id
    @EqualsAndHashCode.Include
    private String idUsuario;

    private String carrito;
    private List<String> historialCompras;
    private Usuario usuario;
    private List<Notificacion> notificaciones;
    private List<TicketSoporte> ticketsSoporte;
    private RolUsuario rol;
    private CodigoVerificacion codigoVerificacion;
    private EstadoCuenta estado;
    private String imageProfile;

}

