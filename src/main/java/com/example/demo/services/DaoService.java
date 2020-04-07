package com.example.demo.services;

import com.example.demo.model.Mail;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaoService implements IDao{

    @Autowired
    private UserRepository repository;

    @Autowired
    private MailingService mailingService;

    @Override
    public String addEmail(String email) {
        User newUser=new User(email);
        return repository.save(newUser).getEmail();
    }

    @Override
    public void deleteEmail(String email) {
        repository.deleteById(email);
    }

    @Override
    public String sendEmail(String message, String subject) {
        List<User> users=repository.findAll();
        Mail email=new Mail(new String[1], message, subject);
        users.forEach((user)->{
            email.getTo()[0]=user.getEmail();
            mailingService.sendMail(email);
        });
        return "sent successfully";
    }
}
