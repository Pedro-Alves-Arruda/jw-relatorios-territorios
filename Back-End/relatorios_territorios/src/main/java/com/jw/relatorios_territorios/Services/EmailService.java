package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.Models.Relatorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

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

    private String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

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

    public void enviarEmailRelatorioPublicador(Object[] relatorio){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(relatorio[5].toString());
            message.setSubject("Relatorio referente ao mês de "+ this.mesAtual);
            message.setText("Nome: "+relatorio[0]+"\nGrupo de campo: "+relatorio[1]+"\nTrabalhou no campo? sim");
            message.setFrom(this.emailFrom);


            this.mailSender.send(message);
        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void enviarEmailRelatorioPessoal(Object[] relatorio){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(relatorio[5].toString());
            message.setSubject("Relatorio referente ao mês de "+ this.mesAtual);
            message.setText("Não se esqueça de enviar seu relatorio");
            message.setFrom(this.emailFrom);


            this.mailSender.send(message);
        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void enviarEmailRelatorioPioneiro(Object[] relatorio){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(relatorio[5].toString());
            message.setSubject("Relatorio referente ao mês de "+ this.mesAtual);
            message.setText("Nome: "+relatorio[0]+"\nGrupo de campo: "+relatorio[1]+"\nTrabalhou no campo? sim\nHoras trabalhadas: "+relatorio[2]);
            message.setFrom(this.emailFrom);


            this.mailSender.send(message);
        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
