package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImagenesServicioImpl implements ImagenesServicio {

    @Override
    public String subirImagen(MultipartFile multipartFile) throws Exception{

        Bucket bucket = StorageClient.getInstance().bucket();

        String fileName = String.format( "%s-%s", UUID.randomUUID(), multipartFile.getOriginalFilename() );

        Blob blob = bucket.create( fileName, multipartFile.getInputStream(), multipartFile.getContentType() );

        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucket.getName(),
                blob.getName()
        );
    }

    @Override
    public String ActualizarImagen(String urlImagen, MultipartFile multipartFile) throws Exception {

        String fileName = extraerNombreImagen(urlImagen);

        // Obtén la referencia al bucket de Firebase Storage
        Bucket bucket = StorageClient.getInstance().bucket();

        // Comprueba si el archivo ya existe en el bucket
        Blob blob = bucket.get(fileName);
        if (blob == null) {
            throw new Exception("El archivo no existe en el almacenamiento.");
        }

        // Actualiza la imagen en el almacenamiento
        blob = bucket.create(fileName, multipartFile.getInputStream(), multipartFile.getContentType());

        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucket.getName(),
                blob.getName()
        );
    }

    @Override
    public void eliminarImagen(String urlImagen) throws Exception{

        String nombreImagen = extraerNombreImagen(urlImagen);

        // Obtén la referencia al bucket de Firebase Storage
        Bucket bucket = StorageClient.getInstance().bucket();

        // Comprueba si el archivo existe en el bucket
        Blob blob = bucket.get(nombreImagen);
        if (blob == null) {
            throw new Exception("El archivo no existe en el almacenamiento.");
        }

        // Elimina la imagen del almacenamiento
        blob.delete();
    }

    private String extraerNombreImagen(String urlImagen) {

        // Buscar la posición donde empieza el nombre de la imagen (después de "/o/")
        int indiceInicio = urlImagen.indexOf("/o/") + 3;

        // Buscar el final de la imagen antes del parámetro "?alt"
        int indiceFin = urlImagen.indexOf("?alt");

        // Extraer el nombre de la imagen entre "/o/" y "?alt"
        if (indiceInicio != -1 && indiceFin != -1) {
            return urlImagen.substring(indiceInicio, indiceFin);
        } else {
            throw new IllegalArgumentException("URL no contiene una imagen válida");
        }
    }

}
