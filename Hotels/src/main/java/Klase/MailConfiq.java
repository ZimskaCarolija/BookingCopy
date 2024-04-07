/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author Aleksa
 */
public class MailConfiq {
    
    public static  JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
mailSender.setProtocol("smtp");
mailSender.setHost("smtp.gmail.com"); // Direktno koristimo Gmail SMTP server
mailSender.setPort(587); // Gmail STARTTLS port
mailSender.setUsername(System.getenv("EMAIL"));
mailSender.setPassword(System.getenv("PASSWORD"));

Properties props = mailSender.getJavaMailProperties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.debug", "true"); // Korisno za debug, može se isključiti u produkciji

        return mailSender;
    }
}
