package co.edu.uniquindio.unieventos.servicios.interfases;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenesServicio {

    String subirImagen(MultipartFile imagen) throws Exception;
    void eliminarImagen(String nombreImagen) throws Exception;

}
