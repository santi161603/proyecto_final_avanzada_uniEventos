package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    @Query(value = "{ 'usuarioId': ?0, 'items.tipoEvento': ?1 }", fields = "{ 'items.$': 1 }")
    List<Evento> findByUsuarioIdAndTipoEvento(ObjectId usuarioId, String tipoEvento);

    @Query(value = "{ 'categoria' : ?0 }")
    List<Evento> findByCategoria(TipoEvento categoria);


}
