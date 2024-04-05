/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Aleksa
 */
public class Sesije {
    public static void Login(Korisnik k,HttpServletRequest request)
    {
        if(k.getId()<0)
          request.getSession().setAttribute("korisnik", null);
        else
        request.getSession().setAttribute("korisnik", k);
    }
    public static void Loggout(Korisnik k,HttpServletRequest request)
    {
        request.getSession().setAttribute("korisnik", null);
        request.getSession().removeAttribute("korisnik");
    }
    public static  boolean  ProveriOvlascenje(HttpServletRequest request,int idVOlascenja)
    {
        if(request.getSession().getAttribute("korisnik")==null)
        return false;
        Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
        if(k.getId_accType()!=idVOlascenja)
        return false;
        return true;
    }
}
