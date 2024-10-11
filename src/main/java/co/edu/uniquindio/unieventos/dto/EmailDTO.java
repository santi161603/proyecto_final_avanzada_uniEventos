package co.edu.uniquindio.unieventos.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EmailDTO(
        @NotBlank @Length(max = 40) String asunto,
        @NotBlank @Length(max = 200) String cuerpo,
        @NotBlank @Length(max = 20) String destinatario
) {
}
