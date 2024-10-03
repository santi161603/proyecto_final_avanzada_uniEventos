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

    public CuentaTest(CuentaRepository cuentaRepo) {
        this.cuentaRepo = cuentaRepo;
    }

    //TODO se debe implementar los metodos de prueba y los metodos en el repository

    //TODO metodo de ensayo se deme cambiar a nuestro modelo
}

