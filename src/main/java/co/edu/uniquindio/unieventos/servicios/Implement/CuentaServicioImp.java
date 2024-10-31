package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.config.JWTUtils;
import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cuenta;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.vo.CodigoVerificacion;
import co.edu.uniquindio.unieventos.modelo.vo.Usuario;
import co.edu.uniquindio.unieventos.repositorio.CuentaRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.CuentaServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.EmailServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.ImagenesServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServicioImp implements CuentaServicio {

    private final CuentaRepository Cuentarepo;
    private final JWTUtils jwtUtils;
    private final EmailServicio emailServicio;
    private final CuentaRepository cuentaRepository;
    private final ImagenesServicio imagenesServicio;
    private final CarritoServicioImp carritoServicio;

    @Override
    public String crearCuenta(DTOCrearCuenta dtoCrearCuenta) throws Exception {
        // Crear una nueva instancia de Cuenta
        Cuenta nuevaCuenta = new Cuenta();
        // Asignar los valores del DTO a la entidad Cuenta
        nuevaCuenta.setRol(dtoCrearCuenta.rol());
        nuevaCuenta.setEstado(EstadoCuenta.INACTIVO);

        String contrasenaEncrip = encriptarPassword(dtoCrearCuenta.contrasena());

        int codigoVerificacion = generarCodigoVerificacion();

        CodigoVerificacion codigoVerif = new CodigoVerificacion();
        codigoVerif.setCodigo(codigoVerificacion);
        codigoVerif.setFecha(LocalDateTime.now());

        // Crear una instancia de Usuario dentro de Cuenta
        Usuario usuario = new Usuario();
        usuario.setCedula(dtoCrearCuenta.cedula());
        usuario.setNombre(dtoCrearCuenta.nombre());
        usuario.setApellido(dtoCrearCuenta.apellido());
        usuario.setDireccion(dtoCrearCuenta.direccion());
        usuario.setCiudad(dtoCrearCuenta.ciudad());
        usuario.setTelefono(dtoCrearCuenta.telefono());
        usuario.setContrasena(contrasenaEncrip);
        usuario.setEmail(dtoCrearCuenta.email());

        //crear imagen de perfil
        // Ruta del archivo de imagen local
        String filePath = "src/main/resources/Image/usuario.jpg";

        // Cargar el archivo de imagen
        File imageFile = new File(filePath);
        String fileName = imageFile.getName(); // Obtener el nombre del archivo
        String contentType = Files.probeContentType(imageFile.toPath()); // Detectar el tipo de contenido

            // Crear el MultipartFile implementando la interfaz manualmente
            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return fileName;
                }

                @Override
                public String getOriginalFilename() {
                    return fileName;
                }

                @Override
                public String getContentType() {
                    return contentType;
                }

                @Override
                public boolean isEmpty() {
                    return imageFile.length() == 0;
                }

                @Override
                public long getSize() {
                    return imageFile.length();
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return Files.readAllBytes(imageFile.toPath());
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new FileInputStream(imageFile);
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {
                    Files.copy(imageFile.toPath(), dest.toPath());
                }
            };

            // Llamar al método subirImagen
            String imageUrl = imagenesServicio.subirImagen(multipartFile);

            nuevaCuenta.setImageProfile(imageUrl);
            nuevaCuenta.setUsuario(usuario);
            nuevaCuenta.setCodigoVerificacion(codigoVerif);

            // Guardar la cuenta en la base de datos
            Cuentarepo.save(nuevaCuenta);

            String id = nuevaCuenta.getIdUsuario();

            String idCarrito = carritoServicio.crearCarrito(id);

            nuevaCuenta.setCarrito(idCarrito);

            cuentaRepository.save(nuevaCuenta);

            // Enviar el código de verificación por correo
            String asunto = "Código de verificación para activar tu cuenta en UniEventos";
            String cuerpo = "Hola " + dtoCrearCuenta.nombre() + ",\n\n" +
                    "Gracias por registrarte en UniEventos. Tu código de verificación es: " + codigoVerificacion + "\n\n" +
                    "Este código es válido por 15 minutos.";

            EmailDTO emailDTO = new EmailDTO(asunto, cuerpo, dtoCrearCuenta.email());
            try {
                emailServicio.enviarCorreo(emailDTO);
            } catch (Exception e) {
                // Manejar la excepción en caso de fallo en el envío del correo
                throw new RuntimeException("Error al enviar el correo de verificación", e);
            }

            // Retornar el ID del usuario creado
            return nuevaCuenta.getIdUsuario();
    }

    @Override
    public void actualizarCuenta(DTOActualizarCuenta cuentaActualizada) {

        Cuenta cuenta = obtenerPorEmail(cuentaActualizada.email());

        // Actualizar la información del usuario
        Usuario usuario = cuenta.getUsuario();

        if(cuentaActualizada.nombre() != null && !cuentaActualizada.nombre().equals(usuario.getNombre())) {
            usuario.setNombre(cuentaActualizada.nombre());
        }
        if(cuentaActualizada.cedula() != null && !cuentaActualizada.cedula().equals(usuario.getCedula())) {
        usuario.setCedula(cuentaActualizada.cedula());
        }
        if(cuentaActualizada.apellido() != null && !cuentaActualizada.apellido().equals(usuario.getApellido())) {
            usuario.setApellido(cuentaActualizada.apellido());
        }
        if (cuentaActualizada.direccion() != null && !cuentaActualizada.direccion().equals(usuario.getDireccion())) {
            usuario.setDireccion(cuentaActualizada.direccion());
        }
        if(cuentaActualizada.telefono() != null && !cuentaActualizada.telefono().equals(usuario.getTelefono())) {
            usuario.setTelefono(cuentaActualizada.telefono());
        }

        cuenta.setUsuario(usuario);

        // Guardar la cuenta actualizada en la base de datos
        Cuentarepo.save(cuenta);
    }

    private int generarCodigoVerificacion() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Genera un número entre 1000 y 9999
    }

    @Override
    public void eliminarCuenta(String idUsuario) {

        Optional<Cuenta> cuenta = cuentaRepository.findById(idUsuario);

        if (cuenta.isPresent()) {
            cuenta.get().setEstado(EstadoCuenta.ELIMINADO);
        } else {
            throw new RuntimeException("El usuario no existe");
        }
    }

    @Override
    public void reenviarToken(String idUsuario) throws Exception {
        // Buscar la cuenta en el repositorio usando el ID del usuario
        Cuenta cuenta = cuentaRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuenta con ID: " + idUsuario));

        // Generar un nuevo código de verificación
        int nuevoCodigoVerificacion = generarCodigoVerificacion();
        CodigoVerificacion codigoVerif = cuenta.getCodigoVerificacion();

        // Asignar el nuevo código y la fecha actual
        codigoVerif.setCodigo(nuevoCodigoVerificacion);
        codigoVerif.setFecha(LocalDateTime.now());

        // Guardar los cambios en la base de datos
        cuentaRepository.save(cuenta);

        // Enviar el código de verificación por correo
        String asunto = "Nuevo código de verificación para activar tu cuenta en UniEventos";
        String cuerpo = "Hola " + cuenta.getUsuario().getNombre() + ",\n\n" +
                "Tu nuevo código de verificación es: " + nuevoCodigoVerificacion + "\n\n" +
                "Este código es válido por 15 minutos.";

        EmailDTO emailDTO = new EmailDTO(asunto, cuerpo, cuenta.getUsuario().getEmail());

        try {
            emailServicio.enviarCorreo(emailDTO);
        } catch (Exception e) {
            // Manejar la excepción en caso de fallo en el envío del correo
            throw new RuntimeException("Error al enviar el nuevo correo de verificación", e);
        }
    }

    @Override
    public void subirImagenPerfilUsuario(String usuarioId, MultipartFile imagen) throws Exception {

        Optional<Cuenta> usuarioCuenta = cuentaRepository.findById(usuarioId);

        if (usuarioCuenta.isPresent()) {

            Cuenta cuenta = usuarioCuenta.get();

            String uriImagenProfile = imagenesServicio.ActualizarImagen(cuenta.getImageProfile(),imagen);

            cuenta.setImageProfile(uriImagenProfile);

            cuentaRepository.save(cuenta);
        }else {
            throw new RuntimeException("Cuenta no encontrada");
        }
    }

    @Override
    public void restablecerContrasena(RestablecerContrasenaDTO restablecerContrasenaDTO) throws Exception {

        Cuenta cuenta = cuentaRepository.findByUsuarioEmail(restablecerContrasenaDTO.email());

        String nuevaContrasenaEncryp = encriptarPassword(restablecerContrasenaDTO.contrasenaNueva());

        String oldPassword = cuenta.getUsuario().getContrasena();

        if(oldPassword.equals(nuevaContrasenaEncryp)) {
            throw new RuntimeException("Por favor coloque una contraseña diferente a una de sus contraseñas antiguas");
        }

        cuenta.getUsuario().setContrasena(nuevaContrasenaEncryp);

        cuentaRepository.save(cuenta);

    }

    @Override
    public void enviarToken(String correo) throws Exception {

        Cuenta cuenta = cuentaRepository.findByUsuarioEmail(correo);

        int nuevoCodigoVerificacion = generarCodigoVerificacion();
        CodigoVerificacion codigoVerif = cuenta.getCodigoVerificacion();

        // Asignar el nuevo código y la fecha actual
        codigoVerif.setCodigo(nuevoCodigoVerificacion);
        codigoVerif.setFecha(LocalDateTime.now());

        // Guardar los cambios en la base de datos
        cuentaRepository.save(cuenta);

        // Enviar el código de restablecimiento de contraseña por correo
        String asunto = "Restablecimiento de contraseña para tu cuenta en UniEventos";
        String cuerpo = "Hola " + cuenta.getUsuario().getNombre() + ",\n\n" +
                "Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en UniEventos.\n" +
                "Por favor, utiliza el siguiente código para completar el proceso de restablecimiento:\n\n" +
                "Código de verificación: " + nuevoCodigoVerificacion + "\n\n" +
                "Este código es válido por 15 minutos.\n" +
                "Si no solicitaste el restablecimiento de contraseña, ignora este mensaje.\n\n" +
                "Gracias,\n" +
                "El equipo de UniEventos.";


        EmailDTO emailDTO = new EmailDTO(asunto, cuerpo, correo);

        try {
            emailServicio.enviarCorreo(emailDTO);
        } catch (Exception e) {
            // Manejar la excepción en caso de fallo en el envío del correo
            throw new RuntimeException("Error al enviar el nuevo correo de verificación", e);
        }


    }

    @Override
    public void activarCuenta(String idUsuario, int codigoVerificacionRecibido) throws Exception {
        // Buscar la cuenta en el repositorio usando el ID del usuario
        Cuenta cuenta = cuentaRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuenta con ID: " + idUsuario));

        // Obtener el objeto CodigoVerificacion de la cuenta
        CodigoVerificacion codigoVerif = cuenta.getCodigoVerificacion();

        // Validar si el código ha expirado (más de 15 minutos de antigüedad)
        LocalDateTime fechaActual = LocalDateTime.now();
        if (codigoVerif.getFecha().plusMinutes(15).isBefore(fechaActual)) {
            throw new RuntimeException("El código de verificación ha expirado. Solicite uno nuevo.");
        }

        // Validar si el código recibido es igual al guardado en la cuenta
        if (codigoVerif.getCodigo() != codigoVerificacionRecibido) {
            throw new RuntimeException("El código de verificación es incorrecto.");
        }

        // Si el código es correcto, activar la cuenta
        cuenta.setEstado(EstadoCuenta.ACTIVO);

        // Guardar los cambios en la base de datos
        cuentaRepository.save(cuenta);
    }


    // Obtener todas las cuentas
    @Override
    public List<CuentaListadaDTO> obtenerTodasLasCuentas() {
        // Obtener todas las cuentas de la base de datos
        List<Cuenta> cuentas = Cuentarepo.findAll();

        // Convertir cada Cuenta a CuentaListadaDTO
        return cuentas.stream()
                .map(this::mapearACuentaListadaDTO)
                .collect(Collectors.toList());
    }

    private CuentaListadaDTO mapearACuentaListadaDTO(Cuenta cuenta) {

        Usuario usuario  = cuenta.getUsuario();

        return new CuentaListadaDTO(
                usuario.getCedula(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(), // Asumiendo que esto ya es una lista
                usuario.getDireccion(),
                usuario.getEmail(),
                usuario.getCiudad(),
                cuenta.getRol()
        );
    }

    public CuentaListadaDTO obtenerCuentaId(String idUsuario) throws Exception {
        // Obtener todas las cuentas de la base de datos
        Cuenta cuentas = Cuentarepo.findById(idUsuario).orElseThrow(
                () -> new Exception("Cuenta no encontrada con ID: " + idUsuario)
        );

        // Convertir cada Cuenta a CuentaListadaDTO
        return mapearACuentaListadaDTO(cuentas);
    }

    @Override
    public TokenDTO iniciarSesion( LoginDTO loginDTO) throws Exception {

        Cuenta cuenta = obtenerPorEmail(loginDTO.email());

        if (cuenta.getEstado() != EstadoCuenta.ACTIVO) {
            throw new Exception("La cuenta está inactiva. Por favor, actívala para continuar.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if( !passwordEncoder.matches(loginDTO.contrasena(), cuenta.getUsuario().getContrasena()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = construirClaims(cuenta);
        return new TokenDTO( jwtUtils.generarToken(cuenta.getUsuario().getEmail(), map) );
    }

    private String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }

    private Map<String, Object> construirClaims(@NotNull Cuenta cuenta) {
        return Map.of(
                "rol", cuenta.getRol(),
                "nombre", cuenta.getUsuario().getNombre(),
                "id", cuenta.getIdUsuario()
        );
    }
    private @NotNull Cuenta obtenerPorEmail(String email) {
        Cuenta cuenta = Cuentarepo.findByUsuarioEmail(email);
        if (cuenta == null) {
            throw new EntityNotFoundException("No se encontró una cuenta con el email: " + email);
        }
        return cuenta;
    }




}
