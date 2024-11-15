package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    @Query(value = "{ 'tipoEvento' : ?0 }")
    List<Evento> findByCategoria(TipoEvento tipoEvento);

    @Query(value = "{ 'subEvent.localidad' : { $in: ?0 } }")
    List<Evento> findBySubEventoLocalidadIn(List<String> localidadesIds);


}
