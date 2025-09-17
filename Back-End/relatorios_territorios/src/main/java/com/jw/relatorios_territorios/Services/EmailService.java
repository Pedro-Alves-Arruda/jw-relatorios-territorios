package com.jw.relatorios_territorios.Services;


import com.jw.relatorios_territorios.Models.Relatorio;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${fronted}")
    private String frontend;

    @Value("${EMAIL_SERVICE}")
    private String emailFrom;

    @Value("${EMAIL_SERVICE_PASSWORD}")
    private String emailFromPassword;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private String mesAtual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));


    public void send(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        try{
            Address[] addresses = new Address[]{
                    new InternetAddress(email)
            };

            mimeMessage.addFrom(addresses);
            mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
            mimeMessage.setSubject("Redefinição de Senha");

            String html =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "  <meta charset=\"UTF-8\">" +
                            "  <title>Redefinir Senha</title>" +
                            "</head>" +
                            "<body>" +
                            "  <div style=\"display:flex;align-items:center;justify-content:center;\">"+
                                "  <h3 style=\"color:rgba(105, 36, 124, 0.735);\"> Redefinição de Senha</h3>"+
                            "  </div>"+
                            "  <p>Você deve redefinir sua senha em 5 minutos caso contrario o formulario para redefinição de senha expirará e você deverá solicitar novamente o link para redefinição de senha.</p>"+
                            "  <p>Para redefinir a senha clique no botão abaixo:</p>" +
                            "  <div style=\"display: flex; align-items: center;justify-content: center;\">"+
                                "  <a href=\"http://localhost:4200/formulario-redefinicao-senha?char="+token+"\"" +
                                "          style=\"width: 130px;text-decoration: none;cursor:pointer; border-radius:5px; padding:12px; " +
                                "                 background-color:rgba(105, 36, 124, 0.735); color: white;\">" +
                                "    Redefinir Senha" +
                                "  </a>" +
                            "  </div>"+
                            "  <hr>"+
                            "</body>" +
                            "</html>";


            mimeMessage.setContent(html, "text/html; charset=utf-8");

            javaMailSender.send(mimeMessage);

        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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

    public void enviarEmailRelatorioPioneiro(Object[] relatorio, StringBuilder msg){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(relatorio[5].toString());
            message.setSubject("Relatorio referente ao mês de "+ this.mesAtual);
            message.setText(msg.toString());
            message.setFrom(this.emailFrom);


            this.mailSender.send(message);
        } catch (MailSendException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
