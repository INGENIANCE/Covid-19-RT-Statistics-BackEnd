package com.example.demo.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailingService implements IMailingService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public String sendMail(Mail mail){

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getTo());
        msg.setFrom(from);
        msg.setText(mail.getText());
        msg.setSubject(mail.getSubject());

        javaMailSender.send(msg);

        System.out.println("email sent successfully");
        return "email sent successfully";
    }
}
