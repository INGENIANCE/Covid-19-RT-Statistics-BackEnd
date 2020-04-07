package com.example.demo.services;

import com.example.demo.model.Mail;
import org.springframework.scheduling.annotation.Async;

public interface IMailingService {

    @Async
    public String sendMail(Mail mail);
}
