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
import java.util.Base64;

/**
 *
 * @author Aleksa
 */
public class Amenity {
    private int AmenityId;
    private String Ime;
    private String Opis;
    private String Slika;
    private boolean banovan;

    public Amenity(int AmenityId, String Ime, String Opis, String Slika,boolean banovan) {
        this.AmenityId = AmenityId;
        this.Ime = Ime;
        this.Opis = Opis;
        this.Slika = Slika;
        this.banovan = banovan;
    }

    public int getAmenityId() {
        return AmenityId;
    }

    public void setAmenityId(int AmenityId) {
        this.AmenityId = AmenityId;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String Ime) {
        this.Ime = Ime;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String Opis) {
        this.Opis = Opis;
    }

    public String getSlika() {
        return Slika;
    }

    public void setSlika(String Slika) {
        this.Slika = Slika;
    }

    public boolean isBanovan() {
        return banovan;
    }
    public void setBanovan(boolean ban)
    {
        this.banovan = ban;
    }
    public static ArrayList<Amenity> VratiSve() throws SQLException
    {
        ArrayList<Amenity> svi = new ArrayList<>();
        Connection con  = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("Select * from Amenities");
        ResultSet re = st.executeQuery();
        while(re.next())
        {
            int id = re.getInt("amenity_id");
            String ime = re.getString("amenity_name");
            String opis = re.getString("amenity_desc");
            byte[] bitovi = re.getBytes("amenity_image");
            boolean Banovan = re.getBoolean("banovan");
            String slika = Base64.getEncoder().encodeToString(bitovi);
            Amenity a = new Amenity(id,ime,opis,slika,Banovan);
            svi.add(a);
            
        }
        con.close();
        return svi;
    }
    public static Amenity UzmiPOId(int id)throws Exception
    {
         Connection con  = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("Select * from Amenities where amenity_id = ?");
        st.setInt(1, id);
         ResultSet re = st.executeQuery();
         String ime = "",opis="";
         boolean banovan = false;
         int idPOm = -1;
         String slika = "";
       if(re.next())
       {
           idPOm = re.getInt("amenity_id");
           ime = re.getString("amenity_name");
           opis = re.getString("amenity_desc");
           banovan = re.getBoolean("banovan");
           byte[] bitovi = re.getBytes("amenity_image");
           slika  = Base64.getEncoder().encodeToString(bitovi);
       }
        Amenity a = new Amenity(1,ime,opis,slika,banovan);
        con.close();
        return a;
    }
}
