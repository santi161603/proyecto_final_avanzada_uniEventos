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

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServicioImp implements CuentaServicio {

    private final CuentaRepository Cuentarepo;
    private final JWTUtils jwtUtils;
    private final EmailServicio emailServicio;
    private final CuentaRepository cuentaRepository;
    private final ImagenesServicio imagenesServicio;

    @Override
    public String crearCuenta(DTOCrearCuenta dtoCrearCuenta) throws IOException {
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

        // Crear un FileInputStream para leer el archivo
        try (FileInputStream inputStream = new FileInputStream(imageFile)) {
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

            nuevaCuenta.setUsuario(usuario);
            nuevaCuenta.setCodigoVerificacion(codigoVerif);

            // Guardar la cuenta en la base de datos
            Cuentarepo.save(nuevaCuenta);

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
        } catch (Exception e) {
            throw new RuntimeException("Error al intentar subir la imagen");
        }
    }

    @Override
    public Cuenta actualizarCuenta(DTOActualizarCuenta cuentaActualizada) {

        Cuenta cuenta = obtenerPorEmail(cuentaActualizada.email());

        // Actualizar la información del usuario
        Usuario usuario = cuenta.getUsuario();
        usuario.setCedula(cuentaActualizada.cedula());
        usuario.setNombre(cuentaActualizada.nombre());
        usuario.setApellido(cuentaActualizada.apellido());
        usuario.setDireccion(cuentaActualizada.direccion());
        usuario.setTelefono(cuentaActualizada.telefono());
        cuenta.setUsuario(usuario);

        // Guardar la cuenta actualizada en la base de datos
        return Cuentarepo.save(cuenta);
    }

    private int generarCodigoVerificacion() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Genera un número entre 1000 y 9999
    }

    @Override
    public Boolean eliminarCuenta(String idUsuario) {

        if (Cuentarepo.existsById(idUsuario)) {
            Cuentarepo.deleteById(idUsuario);
            return true;  // La cuenta fue eliminada
        } else {
            return false; // La cuenta no fue encontrada
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
    public List<Cuenta> obtenerTodasLasCuentas() {
        // Devolver todas las cuentas almacenadas en la base de datos
        return Cuentarepo.findAll();
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
