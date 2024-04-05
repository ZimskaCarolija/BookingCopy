/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Aleksa
 */
public class Drzava {
    private int id;
    private String naziv;
    private boolean prikazuje_se;

    public Drzava(int id, String naziv, boolean prikazuje_se) {
        this.id = id;
        this.naziv = naziv;
        this.prikazuje_se = prikazuje_se;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isPrikazuje_se() {
        return prikazuje_se;
    }

    public void setPrikazuje_se(boolean prikazuje_se) {
        this.prikazuje_se = prikazuje_se;
    }
    public static ArrayList<Drzava> VratiSveDrzave() throws SQLException
    {
        ArrayList<Drzava> sve  = new ArrayList<>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from country;");
        ResultSet re =  st.executeQuery();
        while(re.next())
        {
            int Id = re.getInt("county_id");
            String Naziv = re.getString("country_name");
            boolean prikazujeSe = re.getBoolean("banovan");
            Drzava dr = new Drzava(Id,Naziv,prikazujeSe);
            sve.add(dr);
        }
        con.close();
        return sve;
    }
    public static Drzava VratiPoId(int id) throws SQLException
    {
        Connection con  = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from country where county_id = ?;");
        st.setInt(1, id);
        ResultSet re = st.executeQuery();
        if(re.next())
        {
            int i = re.getInt("county_id");
            String naziv = re.getString("country_name");
            boolean vidi = re.getBoolean("banovan");
            Drzava dr = new Drzava(i,naziv,vidi);
            con.close();
            return dr;
        }
        throw new SQLException("Id not found");
        
    }
}
