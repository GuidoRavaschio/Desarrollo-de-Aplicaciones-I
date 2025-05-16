package com.uade.tpo.TurnosYa.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);  
        message.setTo(to);  
        message.setSubject(subject); 
        message.setText(text); 

        emailSender.send(message);
    }

    public List<String> createEmailContentForReservations(Long id, String date, String shift, String address, String action){
        String subject = "Reservation n°" + Long.toString(id) + " " + action;
        String one = "Your reservation has been " + action + ":\n";
        String two = "Date: " + date + "\n";
        String three = "Shift: " + shift + "\n";
        String four = "Location: " + address + "\n \n";
        String five = "Thank you for using our page :)";
        String text = one + two + three + four + five;
        return List.of(subject, text);
    }

    public List<String> createEmailContentForLocations(Long id, String address, String name, String action){
        String subject = "Location n°" + Long.toString(id) + " " + action;
        String one = "Your Location has been " + action + ":\n";
        String two = "Name: " + name + "\n";
        String three = "Adress: " + address + "\n";
        String four = "Thank you for using our page :)";
        String text = one + two + three + four;
        return List.of(subject, text);
    }

    public List<String> createEmailContentForUser(String name, String action){
        String subject = name + " " + action;
        String text = "Dear " + name + ", your profile has been " + action;
        return List.of(subject, text);
    }
}