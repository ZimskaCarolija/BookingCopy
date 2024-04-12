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
public class HotelExtended extends Hotel{
    private int MinPrice;
    private int CountryId;
    private String CountryName;
    private String CityName;

    public HotelExtended(int MinPrice, int CountryId, String CountryName,  String CityName, int idHotel, String name, String desc, int city_id, String address, boolean isShowin, String hotel_img, int starts, int userId) {
        super(idHotel, name, desc, city_id, address, isShowin, hotel_img, starts, userId);
        this.MinPrice = MinPrice;
        this.CountryId = CountryId;
        this.CountryName = CountryName;
        this.CityName = CityName;
    }

    public HotelExtended(int idHotel, String name, String desc, int city_id, String address, boolean isShowin, String hotel_img, int starts, int userId) {
        super(idHotel, name, desc, city_id, address, isShowin, hotel_img, starts, userId);
    }
    
    public int getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(int MinPrice) {
        this.MinPrice = MinPrice;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int CountryId) {
        this.CountryId = CountryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }



    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }
  public static ArrayList<HotelExtended> AllHotelExtended(Connection con) throws Exception {
    ArrayList<HotelExtended> all = new ArrayList<>();
    PreparedStatement st = con.prepareStatement("SELECT DISTINCT h.hotel_id, hotel_name, h.city_id, h.adress, h.banovan, h.stars, h.hotel_main_ing, h.hotel_desc, c.city_name, co.county_id, co.country_name, MIN(rt.price) AS price " +
            "FROM hotel h, city c, country co, room_type rt " +
            "WHERE h.city_id = c.city_id AND c.country_id = co.county_id AND rt.hotel_id = h.hotel_id " +
            "AND h.banovan = TRUE AND c.banovan = TRUE AND co.banovan = TRUE AND rt.banovan = TRUE");
    ResultSet rs = st.executeQuery();

    while (rs.next()) {
        int hotelId = rs.getInt("hotel_id");
        String hotelName = rs.getString("hotel_name");
        int cityId = rs.getInt("city_id");
        String address = rs.getString("adress");
        boolean isBanned = rs.getBoolean("banovan");
        int stars = rs.getInt("stars");
        byte[] bites = rs.getBytes("hotel_main_ing");
        String mainIngredient = Base64.getEncoder().encodeToString(bites);
        String description = rs.getString("hotel_desc");
        String cityName = rs.getString("city_name");
        int countryId = rs.getInt("county_id");
        String countryName = rs.getString("country_name");
        int minPrice = rs.getInt("price");

  
        HotelExtended hotelExtended = new HotelExtended(minPrice, countryId, countryName, cityName, hotelId, hotelName, description, cityId, address, isBanned, mainIngredient, stars, -1);
        all.add(hotelExtended);
    }
    return all;
}
  
}
