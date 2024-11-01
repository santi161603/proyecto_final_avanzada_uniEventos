package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public record DTOActualizarImagen(

        @NotBlank(message = "El nombre de la imagen no puede estar vacío")String imageName,
        @NotNull(message = "La imagen no puede ser nula") MultipartFile imagen

) {
}
