package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Cupon;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.Orden;
import co.edu.uniquindio.unieventos.modelo.enums.EstadoCuenta;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.modelo.vo.Pago;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import co.edu.uniquindio.unieventos.modelo.vo.Transaccion;
import co.edu.uniquindio.unieventos.repositorio.CuponRepository;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.repositorio.OrdenRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.*;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdenServicioImp implements OrdenServicio {

    private final OrdenRepository ordenRepository;
    private final EventoServicio eventoServicio;
    private final EventoRepository eventoRepository;
    private final CuponRepository cuponRepository;
    private final CuponServicio cuponServicio;
    private final CarritoServicio carritoServicio;

    @Override
    public String crearOrden(DTOCrearOrden orden) throws Exception {
        // Crear una nueva instancia de la orden
        Orden nuevaOrden = new Orden();

        // Extraer los datos del DTO
        Transaccion transaccion = new Transaccion();
        Pago pago = new Pago();

        pago.setEstadoPago("PENDIENTE");
        pago.setMetodoPago("MercadoPago");

        System.out.println(orden.transaccion().productos().get(0).cupon());

        List<ItemCarritoVO> productosVO = convertirAItemCarritoVO(orden.transaccion().productos());

        double montoTotalSinDescuento = 0;

        for (ItemCarritoDTO item : orden.transaccion().productos()) {

            Evento evento = eventoRepository.findById(item.eventoId()).orElseThrow(() -> new Exception("No se puede realizar el Pago"));

            SubEvento subEvento = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == item.idSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            montoTotalSinDescuento += (subEvento.getPrecioEntrada() * item.cantidadEntradas());

        }

        transaccion.setProductos(productosVO);
        transaccion.setIdCliente(orden.transaccion().idCliente());

        nuevaOrden.setTransaccion(transaccion);
        nuevaOrden.setPago(pago);
        nuevaOrden.setMontoTotalSinDescuento(montoTotalSinDescuento);

        // Guardar la orden en el repositorio (base de datos)
        ordenRepository.save(nuevaOrden);

        return nuevaOrden.getIdOrden();
    }

    // Método para convertir de ItemCarritoDTO a ItemCarritoVO
    private List<ItemCarritoVO> convertirAItemCarritoVO(List<ItemCarritoDTO> productosDTO) {
        return productosDTO.stream()
                .map(this::mapearAItemCarritoVO) // Mapea cada subEvento a DTOSubEventos
                .collect(Collectors.toList());
    }

    private ItemCarritoVO mapearAItemCarritoVO(ItemCarritoDTO itemCarritoDTO) {
        ItemCarritoVO itemCarritoVO = new ItemCarritoVO();
        itemCarritoVO.setEventoId(itemCarritoDTO.eventoId());
        itemCarritoVO.setIdSubevento(itemCarritoDTO.idSubevento());
        itemCarritoVO.setCantidadEntradas(itemCarritoDTO.cantidadEntradas());
        itemCarritoVO.setCupon(itemCarritoDTO.cupon());
        return itemCarritoVO;
    }

    @Override
    public void eliminarOrden(String idOrden) throws Exception {

    }

    @Override
    public void actualizarOrden(String idOrden, DTOActualizarOrden ordenActualizada) throws Exception {

    }

    @Override
    public OrdenInfoDTO obtenerOrdenPorId(String idOrden) throws Exception {

        Orden orden = ordenRepository.findById(idOrden).orElseThrow(() -> new Exception("Orden no encontrada"));

        return mapearAOrdenInfoDTO(orden);

    }

    @Override
    public List<OrdenInfoDTO> obtenerTodasLasOrdenes() throws Exception {
        return null;
    }

    @Override
    public List<OrdenInfoDTO> obtenerTodasLasOrdenerCliente(String idCliente) throws Exception {

        List<Orden> ordenList = ordenRepository.findByIdCliente(idCliente);

        if (ordenList.isEmpty()) {
            throw new Exception("Aun no tienes ordenes disponibles");
        }

        return ordenList.stream()
                .map(this::mapearAOrdenInfoDTO)
                .collect(Collectors.toList());
    }

    private OrdenInfoDTO mapearAOrdenInfoDTO(Orden orden) {

        TransaccionDto transaccionDto = new TransaccionDto(
                convertirAItemCarritoDTO(orden.getTransaccion().getProductos()),
                orden.getTransaccion().getIdCliente()
        );

        LocalDate fechaPago = orden.getPago().getFechaPago() != null ? orden.getPago().getFechaPago() : LocalDate.now();
        String detalleEstadoPago = orden.getPago().getDetalleEstadoPago() != null ? orden.getPago().getDetalleEstadoPago() : "Detalle no disponible";
        String tipoPago = orden.getPago().getTipoPago() != null ? orden.getPago().getTipoPago() : "Tipo no disponible";
        String moneda = orden.getPago().getMoneda() != null ? orden.getPago().getMoneda() : "Moneda no disponible";

        PagoObtenidoDTO pagoDTO = new PagoObtenidoDTO(
                orden.getPago().getMetodoPago(),
                orden.getPago().getEstadoPago(),
                fechaPago,
                detalleEstadoPago,
                tipoPago,
                moneda
        );
        OrdenInfoDTO ordenInfoDTO = new OrdenInfoDTO(
                orden.getIdOrden(),
                transaccionDto,
                pagoDTO,
                orden.getMontoTotal(),
                orden.getMontoTotalSinDescuento()
        );

        return ordenInfoDTO;
    }

    private List<ItemCarritoDTO> convertirAItemCarritoDTO(List<ItemCarritoVO> productos) {
        return productos.stream()
                .map(this::mapearAItemCarritoDTO) // Mapea cada subEvento a DTOSubEventos
                .collect(Collectors.toList());
    }

    private ItemCarritoDTO mapearAItemCarritoDTO(ItemCarritoVO itemCarritoVO) {
        ItemCarritoDTO itemCarritoDTO = new ItemCarritoDTO(
                itemCarritoVO.getEventoId(),
                itemCarritoVO.getIdSubevento(),
                itemCarritoVO.getCantidadEntradas(),
                itemCarritoVO.getCupon()
        );

        return itemCarritoDTO;
    }

    @Override
    public String crearOrdenDesdeCarrito(String usuarioid) throws Exception {

        CarritoObtenidoDTO carritoObtenidoDTO = carritoServicio.obtenerCarrito(usuarioid);

        Orden orden = new Orden();
        Transaccion transaccion = new Transaccion();
        Pago pago = new Pago();

        pago.setEstadoPago("PENDIENTE");
        pago.setMetodoPago("MercadoPago");

        double montoTotalSinDescuento = 0;

        for (ItemCarritoVO item : carritoObtenidoDTO.items()) {

            System.out.println("estoy aqui");
            System.out.println(item.getIdSubevento());
            Evento evento = eventoRepository.findById(item.getEventoId()).orElseThrow(() -> new Exception("No se puede realizar el Pago"));

            SubEvento subEvento = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == item.getIdSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            montoTotalSinDescuento += (subEvento.getPrecioEntrada() * item.getCantidadEntradas());

        }

        transaccion.setProductos(carritoObtenidoDTO.items());
        transaccion.setIdCliente(usuarioid);

        orden.setTransaccion(transaccion);
        orden.setPago(pago);
        orden.setMontoTotalSinDescuento(montoTotalSinDescuento);

        ordenRepository.save(orden);

        return orden.getIdOrden();
    }

    private boolean validarEvento(SubEvento subEvento) {
        // Fecha actual
        LocalDate hoy = LocalDate.now();
        System.out.println("Hoy: " + hoy);

        // Fecha dos días después
        LocalDate dosDiasDespues = hoy.plusDays(2);
        System.out.println("Dos días después: " + dosDiasDespues);


        LocalDate fechaSubevento = subEvento.getFechaEvento();
        System.out.println("Fecha subevento: " + fechaSubevento);

        // Verificar si el subevento está en el rango de hoy a dos días después
        boolean isWithinRange = fechaSubevento.isBefore(hoy) && fechaSubevento.isBefore(dosDiasDespues);
        System.out.println("Subevento está dentro del rango: " + isWithinRange);

        // Solo incluir si el estado es "ACTIVO" y está dentro del rango
        return subEvento.getEstadoSubevento().equals(EstadoCuenta.ACTIVO) && isWithinRange;

    }

    @Override
    public Preference realizarPago(String idOrden) throws Exception {

        // Obtener la orden guardada en la base de datos y los ítems de la orden
        Optional<Orden> ordenGuardada = ordenRepository.findById(idOrden);

        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();

        if (ordenGuardada.isEmpty()) {
            throw new Exception("No se puede realizar el Pago");
        }

        // Recorrer los items de la orden y crea los ítems de la pasarela
        for (ItemCarritoVO item : ordenGuardada.get().getTransaccion().getProductos()) {

            // Obtener el evento y la localidad del ítem
            Evento evento = eventoRepository.findById(item.getEventoId()).orElseThrow(() -> new Exception("No se puede realizar el Pago"));

            SubEvento subEvento = evento.getSubEvent().stream()
                    .filter(sub -> sub.getIdSubEvento() == item.getIdSubevento())
                    .findFirst()
                    .orElseThrow(() -> new Exception("Subevento no encontrado"));

            double valorEntrada = subEvento.getPrecioEntrada();

            System.out.println("este es el cupon: " + item.getCupon());
            System.out.println(item);
            System.out.println(valorEntrada);
            if (item.getCupon() != null && !item.getCupon().isEmpty()) {

                System.out.println(item.getCupon() + " estoy donde el cupon");

                Cupon cupon;
                if (item.getCupon().equals("BIENVENIDO") || item.getCupon().equals("PRIMERACOMPRA")) {
                    cupon = cuponRepository.findByNombreCuponAndUserCupon(item.getCupon(), ordenGuardada.get().getTransaccion().getIdCliente()).orElseThrow(() -> new Exception("Cupon no encontrado"));
                    System.out.println("entontrado bienvenido");
                } else {
                    cupon = cuponRepository.findByNombreCupon(item.getCupon()).orElseThrow(() -> new Exception("Cupon no encontrado"));
                    System.out.println("encontrado:" + cupon);
                }
                System.out.println(cupon.getPorcentajeDescuento() + "este es el porcentaje de descuento");
                if (cupon.getCantidad() > 0) {
                    valorEntrada -= subEvento.getPrecioEntrada() * (cupon.getPorcentajeDescuento() / 100);
                } else {
                    throw new Exception("No se puede realizar el Pago, Uno de los cupones ya no tiene existencias");
                }
            }

            if (item.getCantidadEntradas() > subEvento.getCantidadEntradas()) {
                throw new Exception("Lo sentimos pero la cantidad de entradas solicitadas es mayor a la cantidad disponible actual que es de: " + subEvento.getCantidadEntradas());
            }

            if (!subEvento.getEstadoSubevento().equals(EstadoCuenta.ACTIVO)) {
                throw new Exception("El evento no se encuentra activo por ello no podemos realizar la compra");
            }
            if (!validarEvento(subEvento)){
                throw new Exception("No se puede realizar la compra ya que el estado a cambiado o son 2 dias antes");
            }

                System.out.println("este es el valor de la entrada: " + valorEntrada);

            // Crear el item de la pasarela
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(item.getEventoId())
                            .title(evento.getNombre())
                            .pictureUrl(evento.getImagenPoster())
                            .categoryId(evento.getTipoEvento().name())
                            .quantity(item.getCantidadEntradas())
                            .currencyId("COP")
                            .unitPrice(BigDecimal.valueOf(valorEntrada))
                            .build();


            itemsPasarela.add(itemRequest);
        }


        // Configurar las credenciales de MercadoPago
        MercadoPagoConfig.setAccessToken("APP_USR-6063355990883321-111217-012ebd59fdcc3293ad6099225062f15d-2080630372");


        // Configurar las urls de retorno de la pasarela (Frontend)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://unieventos-d397d.web.app/pago-exitoso")
                .failure("https://unieventos-d397d.web.app/pago-fallido")
                .pending("https://unieventos-d397d.web.app/pago-pendiente")
                .build();


        // Construir la preferencia de la pasarela con los ítems, metadatos y urls de retorno
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrls)
                .items(itemsPasarela)
                .metadata(Map.of("id_orden", ordenGuardada.get().getIdOrden()))
                .notificationUrl("https://proyecto-final-avanzada-unieventos.onrender.com/servicios/cuenta-no-autenticada/notificacion-mercado-pago")
                .build();


        // Crear la preferencia en la pasarela de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);


        Orden ordenActualizada = ordenGuardada.get();
        ordenActualizada.setCodigoPasarela(preference.getId());
        ordenRepository.save(ordenActualizada); // Asegúrate de usar el repositorio correcto


        return preference;

    }

    @Override
    public void recibirNotificacionMercadoPago(Map<String, Object> request) {
        try {

            System.out.println(request);

            // Obtener el tipo de notificación
            Object tipo = request.get("type");


            // Si la notificación es de un pago entonces obtener el pago y la orden asociada
            if ("payment".equals(tipo)) {


                // Capturamos el JSON que viene en el request y lo convertimos a un String
                String input = request.get("data").toString();

                // Extraemos los números de la cadena, es decir, el id del pago
                String idPago = input.replaceAll("\\D+", "");

                // Se crea el cliente de MercadoPago y se obtiene el pago con el id
                PaymentClient client = new PaymentClient();
                Payment payment = client.get(Long.parseLong(idPago));


                // Obtener el id de la orden asociada al pago que viene en los metadatos
                String idOrden = payment.getMetadata().get("id_orden").toString();


                // Se obtiene la orden guardada en la base de datos y se le asigna el pago
                Orden orden = ordenRepository.findById(idOrden).orElseThrow(
                        () -> new Exception("Orden no encontrada con ID: " + idOrden));
                reducirCantidad(payment, orden);
                Pago pago = crearPago(payment);
                orden.setPago(pago);
                orden.setMontoTotal(payment.getTransactionAmount().floatValue());
                ordenRepository.save(orden);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Pago crearPago(Payment payment) {
        Pago pago = new Pago();
        pago.setPagoCodigo(payment.getId().toString());
        pago.setFechaPago(payment.getDateCreated().toLocalDate());
        pago.setEstadoPago(payment.getStatus());
        pago.setMetodoPago("MercadoPago");
        pago.setDetalleEstadoPago(payment.getStatusDetail());
        pago.setTipoPago(payment.getPaymentTypeId());
        pago.setMoneda(payment.getCurrencyId());
        pago.setCodifoAutorizacion(payment.getAuthorizationCode());
        return pago;

    }

    private void reducirCantidad(Payment payment, Orden orden) throws Exception {
        System.out.println("Aqui estoy pago" + payment.getStatus());
        if (payment.getStatus().equals("approved")) {
            for (ItemCarritoVO item : orden.getTransaccion().getProductos()) {
                eventoServicio.reducirCantidadEntradasSubEvento(item);

                if (item.getCupon() != null && !item.getCupon().isEmpty()) {
                    Cupon cupon;
                    if (item.getCupon().equals("BIENVENIDO") || item.getCupon().equals("PRIMERACOMPRA")) {
                        cupon = cuponRepository.findByNombreCuponAndUserCupon(item.getCupon(), orden.getTransaccion().getIdCliente()).orElseThrow(() -> new Exception("Cupon no encontrado"));
                    } else {
                        cupon = cuponRepository.findByNombreCupon(item.getCupon()).orElseThrow(() -> new Exception("Cupon no encontrado"));
                    }
                    cuponServicio.reducirCantidadCupon(cupon.getCodigoCupon());
                }

            }
        }
    }
}
