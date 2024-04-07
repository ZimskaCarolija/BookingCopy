/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KOntroleri;

import Klase.EmailService;
import Klase.Korisnik;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aleksa
 */
@RestController
@RequestMapping("/Email")
public class MailController {
    
    private final EmailService emailService;

    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }
    
    @PostMapping("/Verify")
    public ResponseEntity<String> sendEmail(HttpServletRequest request) {
        try {
            // Your logic to check if user is logged in
            if (request.getSession().getAttribute("korisnik") == null) {
                throw new Exception("You are not logged in");
            }

            // Assuming 'Korisnik' contains an email field
            Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
            
            // Sending email
            emailService.sendEmail(k.getEmail(), "Verify", "This is a test");
            
            return ResponseEntity.ok("Check your mail");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
