package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.subEvento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    @Query(value = "{ 'categoria' : ?0 }")
    List<Evento> findByCategoria(TipoEvento categoria);

    @Query(value = "{ 'subEvent.fechaEvento' : ?0 }", fields = "{ 'subEvent.$': 1 }")
    Optional<subEvento> findBySubEventoFecha(LocalDateTime localDateTime);

}
