package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImagenServicioTest {

    @Autowired
    private ImagenesServicio imagenesServicio;

    @Test
    public void subirImagentest() throws Exception{

        // Ruta del archivo de imagen local
        String filePath = "src/main/resources/Image/pexels-apasaric-2411688.jpg";

        // Cargar el archivo de imagen
        File imageFile = new File(filePath);
        String fileName = imageFile.getName(); // Obtener el nombre del archivo
        String contentType = Files.probeContentType(imageFile.toPath()); // Obtener el tipo de contenido

        // Crear MultipartFile a partir del archivo
        MultipartFile multipartFile;
        try (FileInputStream inputStream = new FileInputStream(imageFile)) {
            multipartFile = new MockMultipartFile(fileName, fileName, contentType, inputStream);
        }

        // Llamar al método subirImagen
        String imageUrl = imagenesServicio.subirImagen(multipartFile);

        // Comprobar que la URL generada no sea nula y contenga la parte esperada
        assertNotNull(imageUrl);
        assertTrue(imageUrl.contains("https://firebasestorage.googleapis.com/v0/b/")); // Verificar que contenga el prefijo de la URL

        // Por ejemplo, verificar que la URL contenga el nombre del bucket o el nombre del archivo
    }

    @Test
    public void actualizarImagenTest() throws Exception{

        String nombreImagen = "4cec8585-cf52-433c-b36d-422be264b5cf-pexels-apasaric-2411688.jpg";

        String filePath = "src/main/resources/Image/mD.jpg";

        // Cargar el archivo de imagen
        File imageFile = new File(filePath);
        String fileName = imageFile.getName(); // Obtener el nombre del archivo
        String contentType = Files.probeContentType(imageFile.toPath()); // Obtener el tipo de contenido

        // Crear MultipartFile a partir del archivo
        MultipartFile multipartFile;
        try (FileInputStream inputStream = new FileInputStream(imageFile)) {
            multipartFile = new MockMultipartFile(fileName, fileName, contentType, inputStream);
        }

        String uri = imagenesServicio.ActualizarImagen(nombreImagen, multipartFile);

        assertNotNull(uri);
    }

    @Test
        public void eliminarImagen() throws Exception {
        String nombreImagen = "4cec8585-cf52-433c-b36d-422be264b5cf-pexels-apasaric-2411688.jpg";

        // Llamar al método eliminarImagen
        imagenesServicio.eliminarImagen(nombreImagen);

        // Comprobar que la imagen ya no existe en el bucket
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(nombreImagen);

        // La imagen debería ser nula después de la eliminación
        assertNull(blob);
    }


}


