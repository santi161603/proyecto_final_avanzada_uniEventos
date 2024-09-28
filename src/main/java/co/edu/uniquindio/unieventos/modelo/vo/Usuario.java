package co.edu.uniquindio.unieventos.modelo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Usuario {
    private String cedula;
    private String nombre;
    private String direccion;
    private List<String> telefono;
    private String email;
    private String contrasena;
}
