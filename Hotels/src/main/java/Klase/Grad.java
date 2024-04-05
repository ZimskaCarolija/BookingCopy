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
public class Grad {
    private int gradId;
    private String gradNaziv;
    private int countryId;
    private boolean prikazuje_se;

    public Grad(int gradId, String gradNaziv, int countryId, boolean prikazuje_se) {
        this.gradId = gradId;
        this.gradNaziv = gradNaziv;
        this.countryId = countryId;
        this.prikazuje_se = prikazuje_se;
    }

    public int getGradId() {
        return gradId;
    }

    public void setGradId(int gradId) {
        this.gradId = gradId;
    }

    public String getGradNaziv() {
        return gradNaziv;
    }

    public void setGradNaziv(String gradNaziv) {
        this.gradNaziv = gradNaziv;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public boolean isPrikazuje_se() {
        return prikazuje_se;
    }

    public void setPrikazuje_se(boolean prikazuje_se) {
        this.prikazuje_se = prikazuje_se;
    }
    public static void DodajGrad(int idDrzava,String naziv) throws SQLException
    {
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("insert into city(city_name,country_id) values(?,?);");
        st.setString(1, naziv);
        st.setInt(2, idDrzava);
        st.executeUpdate();
        con.close();
    }
    public static ArrayList<Grad> SviGradovi() throws SQLException
    {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from city;");
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int id = rs.getInt("city_id");
            String naziv = rs.getString("city_name");
            int drzava =  rs.getInt("country_id");
            boolean vidi = rs.getBoolean("banovan");
            Grad grad = new Grad(id,naziv,drzava,vidi);
            gradovi.add(grad);
        }
        con.close();
        return gradovi;
    }
        public static Grad UzmiGradId(int id) throws SQLException
    {
        
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from city where city_id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if(rs.next())
        {
            int id2 = rs.getInt("city_id");
            String naziv = rs.getString("city_name");
            int drzava =  rs.getInt("country_id");
            boolean vidi = rs.getBoolean("banovan");
            Grad grad = new Grad(id2,naziv,drzava,vidi);
            con.close();
            return grad;
        }
       throw new SQLException("id does not exists");
        
    }
        public static void IzmenaGrada(int gradId,int drzavaId,String naziv,boolean banovan) throws SQLException
        {
            Connection con = Konekcija.VratiKonekciju();
            PreparedStatement st = con.prepareStatement("update city set city_name=?  ,country_id = ?, banovan = ? where city_id = ?;");
            st.setString(1, naziv);
            st.setInt(2,drzavaId);
            st.setBoolean(3, banovan);
            st.setInt(4, gradId);
            st.executeUpdate();
            con.close();
        }
}
