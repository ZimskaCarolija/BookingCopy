/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import org.springframework.util.Base64Utils;

/**
 *
 * @author Aleksa
 */
public class Korisnik {
    private int id;
    private String email;
    private String phone;
    private int id_accType;
    private boolean verified;
    private boolean ban;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId_accType() {
        return id_accType;
    }

    public void setId_accType(int id_accType) {
        this.id_accType = id_accType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public Korisnik(int id, String email, String phone, int id_accType, boolean verified, boolean ban) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.id_accType = id_accType;
        this.verified = verified;
        this.ban = ban;
    }

    public Korisnik() {
        this.id = -1;
            this.email = "";
        this.phone = "0";
        this.id_accType = 1;
        this.verified = false;
        this.ban = false;
    }
     public static boolean ImaEmail(String mail)
    {
      Connection connection = Konekcija.VratiKonekciju();
    try {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
        st.setString(1, mail);
        ResultSet rs = st.executeQuery();
        boolean postojiEmail = rs.next(); // Provera da li postoji red sa datim e-mailom
        connection.close();
        return postojiEmail;
    } catch (Exception ex) {
        // Obrada izuzetaka ako je potrebno
        ex.printStackTrace(); // Ili bilo koja druga obrada izuzetka
        return false; // Vrati false ako se dogodi izuzetak
    }
    }
    public static Korisnik LoginProvera(String mail,String password)
    {
        Korisnik k =new Korisnik();
        
        Connection connection = Konekcija.VratiKonekciju();
     try
     {
     PreparedStatement st = connection.prepareStatement("Select * from Users where email = ? and pass = Password(?);");
     st.setString(1,mail);
     st.setString(2,password);
     ResultSet rs = st.executeQuery();
     if(rs.next())
     {
         k.setEmail(rs.getString("email"));
         k.setId(rs.getInt("user_id"));
         k.setPhone(rs.getString("phone"));
         k.setId_accType(rs.getInt("acc_type_id"));
         k.setVerified(rs.getBoolean("verified"));
         k.setBan(rs.getBoolean("banovan"));
        connection.close();
         return k;
     }
     connection.close();
     }
     catch(Exception ex)
     {
     return k;
     }
        return k;
    }
    public static String NapraviNalog(String email,String password,HttpServletRequest request)
    {
        //ova funkcija vraca flag 0 emial vec posotji  1 uspelo 2 greska
       if(!Korisnik.ImaEmail(email))
       {
           Connection con = Konekcija.VratiKonekciju();
           try
           {
           PreparedStatement st = con.prepareStatement("insert into Users(email,pass,phone,verified,acc_type_id) VALUES(?,PASSWORD(?),'0',false,1)",Statement.RETURN_GENERATED_KEYS);
           st.setString(1, email);
           st.setString(2, password);
           int affectedRows = st.executeUpdate();
           if(affectedRows<1)
           {
               return "2";
           }
           else
               //Uzimanje podataka o onalogu
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
            if (generatedKeys.next()) {
            long id = generatedKeys.getLong(1);
            Korisnik k = VratiPoId((int) id);
            Sesije.Login(k, request);
             return "1";
             } else {
            throw new Exception("Creating user failed, no ID obtained.");
            }
            }
         
              
           }
           catch(Exception ex)
           {
               return ex.getMessage();
           }
       }
      else
       {
           return "Account with that mail already exists";
       }
    }
    public static Korisnik VratiPoId(int id)
    {
            Korisnik k =new Korisnik();
            Connection con = Konekcija.VratiKonekciju();
           try
           {
           PreparedStatement st = con.prepareStatement("Select * from Users where user_id =?;");
           st.setInt(1, id);
           ResultSet rs = st.executeQuery();
           if(rs.next())
     {
         k.setEmail(rs.getString("email"));
         k.setId(rs.getInt("user_id"));
         k.setPhone(rs.getString("phone"));
         k.setId_accType(rs.getInt("acc_type_id"));
         k.setVerified(rs.getBoolean("verified"));
         k.setBan(rs.getBoolean("banovan"));
         rs.close();
        con.close();
         return k;
     }
           con.close();
           return k;
           }
           catch(Exception ex)
           {
               return k;
           }
           
    }
    public static String VratiSliku(int id)
    {
         Connection con = Konekcija.VratiKonekciju();
    try {
        PreparedStatement st = con.prepareStatement("Select acc_image from users where user_id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            byte[] bitovi = rs.getBytes("acc_image");
            return Base64.getEncoder().encodeToString(bitovi);
        }
        // Ako ne postoji podatak u ResultSet-u, vratite prazan string
        return "";
    } catch(Exception ex) {
        ex.printStackTrace(); // ili logujte izuzetak na neki drugi način
        return ex.getMessage();
    } finally {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage(); // ili logujte izuzetak na neki drugi način
        }
    }
    }
    public static ArrayList<KorisnikEmail> UzmiKOrisnike(String pretrazivanje) throws SQLException
    {
        ArrayList<KorisnikEmail> korisnici = new ArrayList<>();
        Connection con = Konekcija.VratiKonekciju();
        String s = "%"+pretrazivanje+"%";
        PreparedStatement st = con.prepareStatement("SELECT * FROM users where email  Like ?;");
        st.setString(1, s);
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int id = rs.getInt("user_id");
            String email = rs.getString("email");
            KorisnikEmail k = new KorisnikEmail(id,email);
            korisnici.add(k);
        }
        con.close();
        return korisnici;
    }
    
}
