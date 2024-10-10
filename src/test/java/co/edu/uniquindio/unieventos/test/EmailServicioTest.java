package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.dto.EmailDTO;
import co.edu.uniquindio.unieventos.servicios.interfases.EmailServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class EmailServicioTest {

    @Autowired
    private EmailServicio emailServicio;


    @Test
    public void EnviarEmailtest() throws Exception {

        EmailDTO emailDTO = new EmailDTO("Prueba","Esto es una prueba","garciaflorezz24@gmail.com");
        emailServicio.enviarCorreo(emailDTO);

    }
    @Test
    public void EnviarEmailImagentest() throws Exception {
        EmailDTO emailDTO = new EmailDTO("Prueba","Esto es una prueba","garciaflorezz24@gmail.com");
        String reference = "https://cdn.pixabay.com/photo/2022/06/11/09/20/snake-7256057_1280.jpg";
        emailServicio.enviarCorreoImagen(emailDTO, reference);
    }
}

