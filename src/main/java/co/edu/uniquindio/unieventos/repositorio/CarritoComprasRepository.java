package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoComprasRepository extends MongoRepository<CarritoCompras,String> {

    @Query(value = "{ 'usuarioId' : ?0 }")
    CarritoCompras findByUsuarioId(String usuarioId);

}
