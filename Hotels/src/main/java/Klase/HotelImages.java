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
public class HotelImages {
    private int hotel_id;
    private int hotel_img_id;
    private String image;

    public HotelImages(int hotel_id, int hotel_img_id, String image) {
        this.hotel_id = hotel_id;
        this.hotel_img_id = hotel_img_id;
        this.image = image;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getHotel_img_id() {
        return hotel_img_id;
    }

    public void setHotel_img_id(int hotel_img_id) {
        this.hotel_img_id = hotel_img_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public static ArrayList<HotelImages> returnHotelImagesByH_ID(int HotelID) throws SQLException
    {
        ArrayList<HotelImages> hotels = new ArrayList<HotelImages>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from hotelimage where hotel_id = ?");
        st.setInt(1, HotelID);
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int hId  =rs.getInt("hotel_id");
            int hotel_img_id = rs.getInt("hotel_img_id");
            byte[] array = rs.getBytes("image");
            String image = Base64.getEncoder().encodeToString(array);
            HotelImages h = new HotelImages(hId,hotel_img_id,image);
            hotels.add(h);
        }
        con.close();
        return hotels;
    }
}
