package co.edu.uniquindio.unieventos.controladores;


import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicios/obtener-enums")
public class EnumsControlador {

    @GetMapping("/get-roles")
    public List<RolUsuario> getRoles() {
        return Arrays.asList(RolUsuario.values());
    }

    @GetMapping("/get-ciudades")
    public List<Ciudades> getCiudades() {
        return Arrays.asList(Ciudades.values());
    }

}
