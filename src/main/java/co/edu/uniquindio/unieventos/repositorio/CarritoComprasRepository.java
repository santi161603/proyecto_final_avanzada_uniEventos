package co.edu.uniquindio.unieventos.repositorio;

import co.edu.uniquindio.unieventos.modelo.documentos.CarritoCompras;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoComprasRepository extends MongoRepository<CarritoCompras,String> {
    // Consulta para buscar un carrito por el ObjectId del usuario
    @Query(value = "{ 'usuarioId' : ?0 }")
    CarritoCompras findByUsuarioId(ObjectId usuarioId);
}
