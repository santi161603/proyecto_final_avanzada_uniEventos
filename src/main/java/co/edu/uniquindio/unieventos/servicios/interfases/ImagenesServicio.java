package co.edu.uniquindio.unieventos.servicios.interfases;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImagenesServicio {

    String subirImagen(MultipartFile imagen) throws Exception;

    String ActualizarImagen(String fileName, MultipartFile multipartFile) throws Exception;

    void eliminarImagen(String nombreImagen) throws Exception;

    String subirImagenDesdeArchivo(File file) throws Exception;
}
