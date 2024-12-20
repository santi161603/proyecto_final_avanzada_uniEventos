package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {

    @Query(value = "{ 'usuario.email' : ?0 }")
    Cuenta findByUsuarioEmail(String email);

}
