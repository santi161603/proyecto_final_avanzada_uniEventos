package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarLocalidad;
import co.edu.uniquindio.unieventos.dto.DTOCrearLocalidad;
import co.edu.uniquindio.unieventos.dto.LocalidadEventoObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.NombreyIdLocalidadObtenidaDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.repositorio.LocalidadRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (localidad.imageLocalidad() == null) {
            localidadEvento.setImageLocalidad("https://firebasestorage.googleapis.com/v0/b/unieventos-d397d.appspot.com/o/59397e25c9a392b78c1528abc99e3cc0.jpg?alt=media&token=d5e2991f-c54b-41dc-9305-bf1cdb2a7de6");
        }else {
            localidadEvento.setImageLocalidad(localidad.imageLocalidad());
        }
        localidadEvento.setCapacidadMaxima(localidad.capacidadMaxima());
        localidadEvento.setCapacidadDisponible(localidad.capacidadDisponible());

        localidadRepository.save(localidadEvento);

    }

    @Override
    public List<LocalidadEventoObtenidoDTO> obtenerLocalidades() throws Exception {

        List<LocalidadEvento> localidadEventos =  localidadRepository.findAll();

        return localidadEventos.stream()
                .map(this::mapearALocalidadEventoObtenidoDTO) // Mapea cada localidad a LocalidadEventoObtenidoDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista
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
    public LocalidadEventoObtenidoDTO obtenerLocalidadPorId(String localidadId) throws Exception {

        LocalidadEvento localidadEvento = localidadRepository.findById(localidadId).orElseThrow(() -> new Exception("Evento no encontrado con ID: " + localidadId));

      return mapearALocalidadEventoObtenidoDTO(localidadEvento);

    }

    @Override
    public List<LocalidadEventoObtenidoDTO> obtenerLocalidadesPorCiudad(Ciudades ciudad) throws Exception {
        List<LocalidadEvento> localidadEventos = localidadRepository.findByCity(ciudad);

        return localidadEventos.stream()
                .map(this::mapearALocalidadEventoObtenidoDTO) // Mapea cada localidad a LocalidadEventoObtenidoDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

    @Override
    public List<NombreyIdLocalidadObtenidaDTO> obtenerTodasLasLocalidadesNombreID() throws Exception {

        List<LocalidadEvento> localidadEventos =  localidadRepository.findAll();

        return localidadEventos.stream()
                .map(this::mapearALocalidadEventoNombreidDTO) // Mapea cada localidad a LocalidadEventoObtenidoDTO
                .collect(Collectors.toList()); // Recoge el resultado en una lista
    }

    private NombreyIdLocalidadObtenidaDTO mapearALocalidadEventoNombreidDTO(LocalidadEvento localidad) {
        return new NombreyIdLocalidadObtenidaDTO(
                localidad.getNombreLocalidad(),
                localidad.getIdLocalidad()
        );
    }

    private LocalidadEventoObtenidoDTO mapearALocalidadEventoObtenidoDTO(LocalidadEvento localidad) {
        return new LocalidadEventoObtenidoDTO(
                localidad.getNombreLocalidad(),
                localidad.getDireccion(),
                localidad.getCiudad(),
                localidad.getImageLocalidad(),
                localidad.getTipoLocalidad(),
                localidad.getCapacidadMaxima(),
                localidad.getCapacidadDisponible()
        );
    }
}
