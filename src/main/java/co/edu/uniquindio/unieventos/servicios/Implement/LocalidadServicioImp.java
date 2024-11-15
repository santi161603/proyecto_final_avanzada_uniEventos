package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.DTOActualizarLocalidad;
import co.edu.uniquindio.unieventos.dto.DTOCrearLocalidad;
import co.edu.uniquindio.unieventos.dto.LocalidadEventoObtenidoDTO;
import co.edu.uniquindio.unieventos.dto.NombreyIdLocalidadObtenidaDTO;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.LocalidadEvento;
import co.edu.uniquindio.unieventos.modelo.enums.Ciudades;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoLocalidad;
import co.edu.uniquindio.unieventos.repositorio.LocalidadRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
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
    private final ImagenesServicio imagenesServicio;

    @Override
    public void crearLocalidad(DTOCrearLocalidad localidad) throws Exception {

        LocalidadEvento localidadEvento = new LocalidadEvento();

        localidadEvento.setNombreLocalidad(localidad.nombreLocalidad());
        localidadEvento.setDireccion(localidad.direccion());
        localidadEvento.setCiudad(localidad.ciudad());
        localidadEvento.setTipoLocalidad(localidad.tipoLocalidad());
        localidadEvento.setEstadoLocalidad(EstadoLocalidad.DISPONIBLE);
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
    public void actualizarLocalidad(DTOActualizarLocalidad localidad, String idLocalidad) throws Exception {

        Optional<LocalidadEvento> localidadEvento = localidadRepository.findById(idLocalidad);

        if(localidadEvento.isPresent()) {

            LocalidadEvento localidadExi = localidadEvento.get();

            if (localidad.nombreLocalidad() != null && !localidad.nombreLocalidad().equals(localidadExi.getNombreLocalidad())) {
                localidadExi.setNombreLocalidad(localidad.nombreLocalidad());
            }
            if (localidad.direccion() != null && !localidad.direccion().equals(localidadExi.getDireccion())) {
                localidadExi.setDireccion(localidad.direccion());
            }
            if(localidad.estadoLocalidad() != null && !localidad.estadoLocalidad().equals(localidadExi.getEstadoLocalidad())){
                localidadExi.setEstadoLocalidad(localidad.estadoLocalidad());
            }
            if (localidad.ciudad() != null && !localidad.ciudad().equals(localidadExi.getCiudad())) {
                localidadExi.setCiudad(localidad.ciudad());
            }
            if (localidad.imageLocalidad() != null && !localidad.imageLocalidad().equals(localidadExi.getImageLocalidad())) {
                if(localidadExi.getImageLocalidad().equals("https://firebasestorage.googleapis.com/v0/b/unieventos-d397d.appspot.com/o/59397e25c9a392b78c1528abc99e3cc0.jpg?alt=media&token=d5e2991f-c54b-41dc-9305-bf1cdb2a7de6")){
                    localidadExi.setImageLocalidad(localidad.imageLocalidad());
                }else {
                    imagenesServicio.eliminarImagen(localidadExi.getImageLocalidad());
                    localidadExi.setImageLocalidad(localidad.imageLocalidad());
                }
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

        if(localidadEvento.isEmpty()){
            throw new Exception("No se encontro la localidad a eliminar");
        }

        LocalidadEvento localidadExi = localidadEvento.get();

        localidadExi.setEstadoLocalidad(EstadoLocalidad.ELIMINADA);
        localidadRepository.save(localidadExi);
    }

    @Override
    public LocalidadEventoObtenidoDTO obtenerLocalidadPorId(String idLocalidad) throws Exception {

        Optional<LocalidadEvento> localidadEvento = localidadRepository.findById(idLocalidad);

        if(localidadEvento.isEmpty()) {
            throw new Exception("No se encontro la localidad"+ idLocalidad);
        }

        LocalidadEvento localidadExi = localidadEvento.get();

      return mapearALocalidadEventoObtenidoDTO(localidadExi);

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
                localidad.getIdLocalidad(),
                localidad.getCiudad(),
                localidad.getImageLocalidad(),
                localidad.getCapacidadDisponible()
        );
    }

    private LocalidadEventoObtenidoDTO mapearALocalidadEventoObtenidoDTO(LocalidadEvento localidad) {
        return new LocalidadEventoObtenidoDTO(
                localidad.getIdLocalidad(),
                localidad.getNombreLocalidad(),
                localidad.getDireccion(),
                localidad.getEstadoLocalidad(),
                localidad.getCiudad(),
                localidad.getImageLocalidad(),
                localidad.getTipoLocalidad(),
                localidad.getCapacidadMaxima(),
                localidad.getCapacidadDisponible()
        );
    }
}
