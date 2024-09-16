package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.Reporte;
import co.edu.uniquindio.proyecto.modelo.vo.TicketSoporte;
import co.edu.uniquindio.proyecto.modelo.vo.Usuario;

import java.util.List;

public class Moderador extends Usuario {
    private List<Reporte> reportesRevisados; // Reportes que ha revisado el moderador
    private List<TicketSoporte> ticketsAsignados; // Tickets de soporte que ha gestionado
    private List<Usuario> usuariosSuspendidos; // Usuarios a los que ha suspendido temporalmente
}
