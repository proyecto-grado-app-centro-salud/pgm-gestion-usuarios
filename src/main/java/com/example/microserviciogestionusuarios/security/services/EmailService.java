package com.example.microserviciogestionusuarios.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("villazante.bernabe.victor@gmail.com");        
        mensaje.setTo(to);
        mensaje.setSubject(subject);
        mensaje.setText("GESTION DE CONSULTAS EXTERNAS Codigo de verificacion:"+text);

        emailSender.send(mensaje);
    }
}
