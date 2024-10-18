package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarLocalidad;
import co.edu.uniquindio.unieventos.dto.DTOCrearLocalidad;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.repositorio.LocalidadRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocalidadServicioImp implements LocalidadServicio {

    private final LocalidadRepository localidadRepository;

    @Override
    public void crearLocalidad(DTOCrearLocalidad localidad) throws Exception {

        LocalidadEvento localidadEvento = new LocalidadEvento();

        localidadEvento.setNombreLocalidad(localidad.nombreLocalidad());
        localidadEvento.setDireccion(localidad.direccion());
        localidadEvento.setCiudad(localidad.ciudad());
        localidadEvento.setTipoLocalidad(localidad.tipoLocalidad());
        localidadEvento.setCapacidadMaxima(localidad.capacidadMaxima());
        localidadEvento.setCapacidadDisponible(localidad.capacidadDisponible());

        localidadRepository.save(localidadEvento);

    }

    @Override
    public List<LocalidadEvento> obtenerLocalidades() throws Exception {
        return localidadRepository.findAll();
    }

    @Override
    public void actualizarLocalidad(DTOActualizarLocalidad localidad, String localidadId) throws Exception {

        Optional<LocalidadEvento> localidadEvento = localidadRepository.findById(localidadId);

        if(localidadEvento.isPresent()) {

            LocalidadEvento localidadExi = localidadEvento.get();

            if (localidad.nombreLocalidad() != null && !localidad.nombreLocalidad().equals(localidadExi.getNombreLocalidad())) {
                localidadExi.setNombreLocalidad(localidad.nombreLocalidad());
            }
            if (localidad.direccion() != null && !localidad.direccion().equals(localidadExi.getDireccion())) {
                localidadExi.setDireccion(localidad.direccion());
            }
            if (localidad.ciudad() != null && !localidad.ciudad().equals(localidadExi.getCiudad())) {
                localidadExi.setCiudad(localidad.ciudad());
            }
            if (localidad.tipoLocalidad() != null && !localidad.tipoLocalidad().equals(localidadExi.getTipoLocalidad())) {
                localidadExi.setTipoLocalidad(localidad.tipoLocalidad());
            }
            if (localidad.capacidadMaxima() == localidadExi.getCapacidadMaxima()) {
                localidadExi.setCapacidadMaxima(localidad.capacidadMaxima());
            }
            if (localidad.capacidadDisponible() != 0 && localidad.capacidadDisponible() == localidadExi.getCapacidadDisponible()) {
                localidadExi.setCapacidadDisponible(localidad.capacidadDisponible());
            }

            localidadRepository.save(localidadExi);
        }else {
            throw new Exception("No se encontro la localidad");
        }

    }

    @Override
    public void eliminarLocalidad(String localidadId) throws Exception {

        Optional<LocalidadEvento> localidadEvento = localidadRepository.findById(localidadId);

        localidadEvento.ifPresent(localidadRepository::delete);

    }

    @Override
    public LocalidadEvento obtenerLocalidadPorId(String localidadId) throws Exception {

        Optional<LocalidadEvento> localidadEvento = localidadRepository.findById(localidadId);

        if(localidadEvento.isEmpty()) return localidadRepository.findById(localidadId).get();
        else{
            throw new Exception("No se encontro la localidad");
        }
    }

    @Override
    public List<LocalidadEvento> obtenerLocalidadesPorCiudad(Ciudades ciudad) throws Exception {
        return localidadRepository.findByCity(ciudad);
    }
}
