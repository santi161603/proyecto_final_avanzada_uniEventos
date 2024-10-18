package co.edu.uniquindio.unieventos.modelo.vo;

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
    private List<String> telefono;
    private String email;
    private String contrasena;

    @Builder
    public Usuario(String cedula, String nombre, List<String> telefono, String direccion, String contrasena, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasena = contrasena;
        this.email = email;
    }
}
