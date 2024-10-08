package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepository extends MongoRepository<LocalidadEvento, String> {
}
