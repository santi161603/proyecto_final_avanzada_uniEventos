package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.documentos.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends MongoRepository<Orden, String> {

    @Query("{ 'transaccion.idCliente' : ?0 }")
    List<Orden> findByIdCliente(String idCliente);

    @Query("{'pago.estadoPago': ?0 }")
    List<Orden> findByPagoEstadoPago(String approved);
}
