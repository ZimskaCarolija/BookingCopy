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
public class Hotel {
    private int idHotel;
    private String name;
    private String desc;
    private int city_id;
    private String address;
    private boolean isShowin;
    private String hotel_img;
    private int starts;
    private int userId;

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsShowin() {
        return isShowin;
    }

    public void setIsShowin(boolean isShowin) {
        this.isShowin = isShowin;
    }

    public String getHotel_img() {
        return hotel_img;
    }

    public void setHotel_img(String hotel_img) {
        this.hotel_img = hotel_img;
    }

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Hotel(int idHotel, String name, String desc, int city_id, String address, boolean isShowin, String hotel_img, int starts, int userId) {
        this.idHotel = idHotel;
        this.name = name;
        this.desc = desc;
        this.city_id = city_id;
        this.address = address;
        this.isShowin = isShowin;
        this.hotel_img = hotel_img;
        this.starts = starts;
        this.userId = userId;
    }
    public static ArrayList<Hotel> HotelMenadzer(int menadzer) throws SQLException
    {
        ArrayList<Hotel> hoteli = new ArrayList<Hotel>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from hotel where userId = ?");
        st.setInt(1, menadzer);
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int idH = rs.getInt("hotel_id");
            String HName = rs.getString("hotel_name");
            int Cid = rs.getInt("city_id");
            String add = rs.getString("adress");
            boolean ban = rs.getBoolean("banovan");
            int zv = rs.getInt("stars");
            byte[] slika = rs.getBytes("hotel_main_ing");
            String slikaH = Base64.getEncoder().encodeToString(slika);
            String desc = rs.getString("hotel_desc");
            int userI = rs.getInt("userId");
            Hotel h = new Hotel(idH,HName,desc,Cid,add,ban,slikaH,zv,userI);
            hoteli.add(h);
        }
        con.close();
        return hoteli;
    }
    public static Hotel VratiHotelId(int id) throws Exception
    {
        
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from hotel where hotel_id = ?");
        st.setInt(1, id);
         ResultSet rs = st.executeQuery();
         if(rs.next())
         {
             int idH = rs.getInt("hotel_id");
            String HName = rs.getString("hotel_name");
            int Cid = rs.getInt("city_id");
            String add = rs.getString("adress");
            boolean ban = rs.getBoolean("banovan");
            int zv = rs.getInt("stars");
            byte[] slika = rs.getBytes("hotel_main_ing");
            String slikaH = Base64.getEncoder().encodeToString(slika);
            String desc = rs.getString("hotel_desc");
            int userI = rs.getInt("userId");
            Hotel h = new Hotel(idH,HName,desc,Cid,add,ban,slikaH,zv,userI);
            con.close();
            return h;
         }
         else
         {
             con.close();
             throw new Exception("Error");
         }
    }
     public static boolean ProveraHotelUser(int idKOrisnik,int hotelId) throws SQLException
    {
    Connection con = Konekcija.VratiKonekciju();
    PreparedStatement st = con.prepareStatement("select 1 from hotel h , users u where h.userId =  u.user_id and u.user_id = ? and h.hotel_id = ?;");
    st.setInt(1, idKOrisnik);
    st.setInt(2, hotelId);
    ResultSet rs = st.executeQuery();
    boolean exists = rs.next(); // Check if a record exists
    con.close();
    return exists;
    }
}
