/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

/**
 *
 * @author Aleksa
 */
public class KorisnikEmail {
    private int id;
    private String mail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public KorisnikEmail(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }
    
}
