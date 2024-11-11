package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.*;
import co.edu.uniquindio.unieventos.modelo.documentos.Evento;
import co.edu.uniquindio.unieventos.modelo.documentos.Orden;
import co.edu.uniquindio.unieventos.modelo.vo.ItemCarritoVO;
import co.edu.uniquindio.unieventos.modelo.vo.SubEvento;
import co.edu.uniquindio.unieventos.modelo.vo.Transaccion;
import co.edu.uniquindio.unieventos.repositorio.EventoRepository;
import co.edu.uniquindio.unieventos.repositorio.OrdenRepository;
import co.edu.uniquindio.unieventos.servicios.interfases.EventoServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.LocalidadServicio;
import co.edu.uniquindio.unieventos.servicios.interfases.OrdenServicio;/*
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;*/
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdenServicioImp implements OrdenServicio {

    private final OrdenRepository ordenRepository;
    private final EventoServicio eventoServicio;
    private final EventoRepository eventoRepository;

    @Override
    public void crearOrden(DTOCrearOrden orden) throws Exception {

        Orden nuevaOrden = new Orden();

        //nuevaOrden.setTransaccion(orden.transaccion());
        //nuevaOrden.setPago(orden.pago());

        // Guardar la orden en el repositorio
        Orden ordenGuardada = ordenRepository.save(nuevaOrden);
    }

    @Override
    public void eliminarOrden(String idOrden) throws Exception {

    }

    @Override
    public void actualizarOrden(String idOrden, DTOActualizarOrden ordenActualizada) throws Exception {

    }

    @Override
    public OrdenInfoDTO obtenerOrdenPorId(String idOrden) throws Exception {
        return null;
    }

    @Override
    public List<OrdenInfoDTO> obtenerTodasLasOrdenes() throws Exception {
        return null;
    }

    @Override
    public void crearOrdenDesdeCarrito(String idCarrito) throws Exception {

    }

    @Override
    public Preference realizarPago(String idOrden) throws Exception {


        // Obtener la orden guardada en la base de datos y los ítems de la orden
        Optional<Orden> ordenGuardada = ordenRepository.findById(idOrden);

        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();

        if(ordenGuardada.isEmpty()){
           throw new Exception("No se puede realizar el Pago");
        }

        // Recorrer los items de la orden y crea los ítems de la pasarela
        for(ItemCarritoVO item : ordenGuardada.get().getTransaccion().getProductos()){

            // Obtener el evento y la localidad del ítem
            EventoObtenidoDTO evento = eventoServicio.obtenerEventoPorId(item.getEventoId());

            SubEvento subEvento = eventoRepository.findBySubEventoFecha(1);

            // Crear el item de la pasarela
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(item.getEventoId())
                            .title(evento.nombre())
                            .pictureUrl(evento.imagenPoster())
                            .categoryId(evento.tipoEvento().name())
                            .quantity(item.getCantidadEntradas())
                            .currencyId("COP")
                            .unitPrice(BigDecimal.valueOf(subEvento.getPrecioEntrada()))
                            .build();


            itemsPasarela.add(itemRequest);
        }


        // Configurar las credenciales de MercadoPago
        MercadoPagoConfig.setAccessToken("APP_USR-3511341566432802-110517-618891a5eff2deb61c786d25fb5d445f-619831412");


        // Configurar las urls de retorno de la pasarela (Frontend)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("URL PAGO EXITOSO")
                .failure("URL PAGO FALLIDO")
                .pending("URL PAGO PENDIENTE")
                .build();


        // Construir la preferencia de la pasarela con los ítems, metadatos y urls de retorno
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrls)
                .items(itemsPasarela)
                .metadata(Map.of("id_orden", ordenGuardada.get().getIdOrden()))
                .notificationUrl("URL NOTIFICACION")
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
       /* try {


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
                Payment payment = client.get( Long.parseLong(idPago) );


                // Obtener el id de la orden asociada al pago que viene en los metadatos
                String idOrden = payment.getMetadata().get("id_orden").toString();


                // Se obtiene la orden guardada en la base de datos y se le asigna el pago
                Orden orden = obtenerOrden(idOrden);
                Pago pago = crearPago(payment);
                orden.setPago(pago);
                ordenRepo.save(orden);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


   /* private Pago crearPago(Payment payment) {
        Pago pago = new Pago();
        pago.setCodigo(payment.getId().toString());
        pago.setFecha( payment.getDateCreated().toLocalDateTime() );
        pago.setEstado(payment.getStatus());
        pago.setDetalleEstado(payment.getStatusDetail());
        pago.setTipoPago(payment.getPaymentTypeId());
        pago.setMoneda(payment.getCurrencyId());
        pago.setCodigoAutorizacion(payment.getAuthorizationCode());
        pago.setValorTransaccion(payment.getTransactionAmount().floatValue());
        return pago;

    }*/
}
