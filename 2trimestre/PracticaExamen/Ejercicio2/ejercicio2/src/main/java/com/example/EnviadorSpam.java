package com.example;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import com.example.LectorParaSpam.ObservadorMensajes;

public class EnviadorSpam implements ObservadorMensajes{
    private String correoEnvio;
    private String contraseñaEnvio;
    private LectorParaSpam lector;

    public EnviadorSpam(String correoEnvio, String contraseñaEnvio, LectorParaSpam lector) {
        this.correoEnvio = correoEnvio;
        this.contraseñaEnvio = contraseñaEnvio;
        this.lector = lector;
    }

    public void actualizar(String info) {
        System.out.println(info);
        String[] informacion = info.split("-");
        try {
            Email email = EmailBuilder.startingBlank()
                    .to("Hola", informacion[0].toString())
                    .from("Ulver", "ulver.calleja@educa.madrid.org")
                    .withReplyTo("Ulver", "ulver.calleja@educa.madrid.org")
                    .withSubject("Proyecto Spamtoso")
                    .withPlainText(informacion[1].toString())
                    .buildEmail();

            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.educa.madrid.org", 587, correoEnvio, contraseñaEnvio)
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .clearEmailValidator() // turns off email validation
                    .buildMailer();
            mailer.sendMail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
