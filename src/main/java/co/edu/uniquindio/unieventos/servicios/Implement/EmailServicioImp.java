package co.edu.uniquindio.unieventos.servicios.Implement;

import co.edu.uniquindio.unieventos.dto.EmailDTO;
import co.edu.uniquindio.unieventos.servicios.interfases.EmailServicio;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.activation.URLDataSource;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URL;


@Service
@Transactional
@RequiredArgsConstructor
public class EmailServicioImp implements EmailServicio {

    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

        Email email = EmailBuilder.startingBlank()
                .from("unieventost@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .buildEmail();
        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "unieventost@gmail.com", "vrqg vfkn neez hfpu")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }
    }

    @Override
    public void enviarCorreoImagen(EmailDTO emailDTO, String reference) throws Exception {

        URL url = new URL(reference);
        DataSource dataSource = new URLDataSource(url);

        Email email = EmailBuilder.startingBlank()
                .from("unieventost@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .withAttachment("orden.jpg", dataSource)
                .buildEmail();
        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "unieventost@gmail.com", "vrqg vfkn neez hfpu")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }
    }

}
