package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CuentaTest {

    @Autowired
    private CuentaRepository cuentaRepo;

    //TODO se debe implementar los metodos de prueba y los metodos en el repository

    //TODO metodo de ensayo se deme cambiar a nuestro modelo

    @Test
    public void registrarTest(){
        //Creamos la cuenta con sus propiedades
        Cuenta cuenta = Cuenta.builder()
                .email("juanito@email.com")
                .password("123456")
                .fechaRegistro(LocalDateTime.now())
                .estado(EstadoCuenta.ACTIVO)
                .usuario(
                        Usuario.builder()
                                .cedula("123")
                                .nombre("Juanito")
                                .direccion("Calle 123")
                                .telefono("121212").build()
                )
                .rol(Rol.CLIENTE).build();


        //Guardamos la cuenta del usuario en la base de datos
        Cuenta cuentaCreada = cuentaRepo.save(cuenta);


        //Verificamos que se haya guardado validando que no sea null
        assertNotNull(cuentaCreada);
    }

}

