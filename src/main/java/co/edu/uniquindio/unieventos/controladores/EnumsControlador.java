package co.edu.uniquindio.unieventos.controladores;


import co.edu.uniquindio.unieventos.modelo.enums.*;
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

    @GetMapping("/get-tipo-evento")
    public List<TipoEvento> getTipoEvento() {
        return Arrays.asList(TipoEvento.values());
    }

    @GetMapping("/get-estado-cuenta")
    public List<EstadoCuenta> getEstadoCuenta() {
        return Arrays.asList(EstadoCuenta.values());
    }

    @GetMapping("/get-tipo-localidad")
    public List<TipoLocalidad> getTipoLocalidad() {
        return Arrays.asList(TipoLocalidad.values());
    }
}
