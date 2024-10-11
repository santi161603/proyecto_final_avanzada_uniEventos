package co.edu.uniquindio.unieventos.servicios.interfases;

import co.edu.uniquindio.unieventos.dto.EmailDTO;

public interface EmailServicio {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
    void enviarCorreoImagen(EmailDTO emailDTO, String reference) throws Exception;

}

