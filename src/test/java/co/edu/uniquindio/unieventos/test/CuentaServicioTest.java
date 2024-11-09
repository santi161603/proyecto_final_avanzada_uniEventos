package co.edu.uniquindio.unieventos.test;

import co.edu.uniquindio.unieventos.dto.DTOCrearCuenta;
import co.edu.uniquindio.unieventos.dto.LoginDTO;
import co.edu.uniquindio.unieventos.dto.TokenDTO;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.RolUsuario;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class CuentaServicioTest {

    @Autowired
    private CuentaServicio cuentaServicio;

    @Test
    public void crearCuentaTest() throws Exception {
        DTOCrearCuenta dto = new DTOCrearCuenta("1005088484", "Sebastian David","España", List.of("3108286354"), "Barrio Miraflorez Carrera 19a #36-3", Ciudades.ABRIAQUI, "davideg3007@gmail.com", "david123");
        //Llamada al método que se está probando
        String idUsuario = cuentaServicio.crearCuenta(dto);
        // Verificación
        assertNotNull(idUsuario);
    }

    @Test
    public void reenviarToken() throws Exception {
        cuentaServicio.reenviarToken("670830e86b8c621efd663bc2");
    }

    @Test
    public void activarCuenta()throws Exception{

       // cuentaServicio.activarCuenta("67084a0bb792395ec850e773",4061);

    }
    
    

    @Test
    public void eliminarCuentaTest() throws Exception{
        cuentaServicio.eliminarCuenta("6704c027f38ff255bf5ca93b");
    }

    @Test
    public void iniciarSesionTest() throws Exception{

        LoginDTO loginDTO = new LoginDTO( "david123","davideg3007@gmail.com");

        TokenDTO tokenDTO = cuentaServicio.iniciarSesion(loginDTO);

        System.out.println(tokenDTO);

        assertNotNull(tokenDTO);

    }



}

