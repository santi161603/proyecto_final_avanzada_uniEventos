package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.documentos.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdenRepository extends MongoRepository<Orden, String> {
}
