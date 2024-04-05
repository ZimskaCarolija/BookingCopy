package KOntroleri;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import Klase.Korisnik;
import Klase.Sesije;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aleksa
 */

@RestController("/korisnik")
public class KorisnikKontroler {
   @PostMapping("/login")
   public boolean Logovanje(@RequestParam("email")String email,@RequestParam("password")String password,HttpServletRequest request)
   {
       Korisnik k = new Korisnik();
        k = Korisnik.LoginProvera(email,password);
        if(k==null || k.getId()<1)
        {
            return false;
        }
        else
        {
            Sesije.Login(k, request);
            return true;
        }
        
   }
   @GetMapping("/a")
   public String a()
   {
       return "bbbbb";
   }
}
