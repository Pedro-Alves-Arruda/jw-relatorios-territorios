package com.jw.relatorios_territorios.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    @Value("${fronted}")
    private String frontend;

    @Value("${EMAIL_SERVICE}")
    private String emailFrom;

    @Value("${EMAIL_SERVICE_PASSWORD}")
    private String emailFromPassword;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void send(String email){

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Redefinição de senha");
            message.setText("Segue abaixo link para redefinição de senha - http://" + this.frontend +"/formulario-redefinicao-senha");
            message.setFrom(this.emailFrom);


            this.mailSender.send(message);
            log.info("Email de redefinição de senha enviado com sucesso");

        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
