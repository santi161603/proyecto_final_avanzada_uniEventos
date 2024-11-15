package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Cupon;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepository extends MongoRepository<Cupon, String> {

    // Query para obtener cupon por nombre
    @Query("{ 'nombreCupon' : ?0 }")
    Optional<Cupon> findByNombreCupon(String nombreCupon);

    // Query para obtener cupon por nombre y userCupon (ID de usuario)
    @Query("{ 'nombreCupon' : ?0, 'userCupon' : ?1 }")
    Optional<Cupon> findByNombreCuponAndUserCupon(String nombreCupon, String userCupon);
}
