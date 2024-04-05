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
public class HotelAmenity {
    private int HotelId;
    private int AmenityId;

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int HotelId) {
        this.HotelId = HotelId;
    }

    public int getAmenityId() {
        return AmenityId;
    }

    public void setAmenityId(int AmenityId) {
        this.AmenityId = AmenityId;
    }

    public HotelAmenity(int HotelId, int AmenityId) {
        this.HotelId = HotelId;
        this.AmenityId = AmenityId;
    }
    public static ArrayList<HotelAmenity> returnByHotelId(int idHotel) throws SQLException
    {
        ArrayList<HotelAmenity>all  = new ArrayList<>();
        return all;
    }
}
