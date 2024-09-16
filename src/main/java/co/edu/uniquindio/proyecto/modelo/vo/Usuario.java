package co.edu.uniquindio.proyecto.modelo.vo;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.modelo.enums.RolUsuario;

public class Usuario {
    String idUsuario;
    String cedula;
    String nombre;
    String direccion;
    String telefono;
    String email;
    String contrasena;
    RolUsuario rol;
    EstadoCuenta estado;
    Notificacion notificacion;
    HistorialActividad historialActividad;


}
