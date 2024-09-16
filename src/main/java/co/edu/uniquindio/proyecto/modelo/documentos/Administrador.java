package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.vo.Reporte;
import co.edu.uniquindio.proyecto.modelo.vo.Usuario;

import java.util.List;

public class Administrador extends Usuario {
    private List<Evento> eventosCreados;
    private List<Reporte> reportesGenerados;
}
