package com.example.demo.controller;

import com.example.demo.model.Mail;
import com.example.demo.model.User;
import com.example.demo.services.DaoService;
import com.example.demo.services.IDao;
import com.example.demo.services.IMailingService;
import com.example.demo.services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private IMailingService mailingService;

    @Autowired
    private IDao daoService;

    @RequestMapping(
            value = "/addUser",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public String addUser(@RequestBody User user){
        return daoService.addEmail(user.getEmail());
    }

}
