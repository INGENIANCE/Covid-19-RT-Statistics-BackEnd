package com.example.demo.services;

public interface IDao {
    String addEmail(String email);
    void deleteEmail(String email);
    String sendEmail(String message, String subject);
}
