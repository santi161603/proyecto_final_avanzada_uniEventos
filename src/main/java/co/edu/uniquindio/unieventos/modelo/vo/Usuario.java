package co.edu.uniquindio.unieventos.modelo.vo;

import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private Ciudades ciudad;
    private List<String> telefono;
    private String email;
    private String contrasena;
}
