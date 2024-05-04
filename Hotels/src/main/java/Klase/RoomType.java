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
public class RoomType {
    private int room_type_id;
    private int no_beds;
    private String room_type_name;
    private String desribe;
    private int hotel_id;
    private boolean banovan;
    private int price;
    
    public RoomType(int room_type_id, int no_beds, String room_type_name, String desribe, int hotel_id, boolean banovan,int price) {
        this.room_type_id = room_type_id;
        this.no_beds = no_beds;
        this.room_type_name = room_type_name;
        this.desribe = desribe;
        this.hotel_id = hotel_id;
        this.banovan = banovan;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public int getNo_beds() {
        return no_beds;
    }

    public void setNo_beds(int no_beds) {
        this.no_beds = no_beds;
    }

    public String getRoom_type_name() {
        return room_type_name;
    }

    public void setRoom_type_name(String room_type_name) {
        this.room_type_name = room_type_name;
    }

    public String getDesribe() {
        return desribe;
    }

    public void setDesribe(String desribe) {
        this.desribe = desribe;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public boolean isBanovan() {
        return banovan;
    }

    public void setBanovan(boolean banovan) {
        this.banovan = banovan;
    }
    public static boolean ProveraVlasnistaSobe(int hotelId , int USerId) throws SQLException
    {
        Connection con = Konekcija.VratiKonekciju();
    PreparedStatement st = con.prepareStatement("SELECT 1 FROM hotel h INNER JOIN Users u ON h.userId = u.user_id WHERE h.hotel_id = ? AND u.user_id = ?");
    st.setInt(1, hotelId);
    st.setInt(2, USerId);
    ResultSet rs = st.executeQuery();
    boolean exists = rs.next(); // Check if a record exists
    con.close();
    return exists;
    }
    public static RoomType VratiPoID(int id) throws Exception
    {
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from room_type where room_type_id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        
        if(rs.next())
        {
                    int no_beds = rs.getInt("no_beds");
                    int room_type_id = rs.getInt("room_type_id");
                    String room_type_name = rs.getString("room_type_name");
                    int hotel_id  = rs.getInt("hotel_id");
                    boolean banovan = rs.getBoolean("banovan");
                    String desribe = rs.getString("desribe");
                    int price = rs.getInt("price");
                    RoomType room = new RoomType(room_type_id,no_beds,room_type_name,desribe,hotel_id,banovan,price);
                    con.close();
                    return room;
        }
        else
            throw new Exception("no room");
    }
    public static boolean ProveraSobaUser(int idKOrisnik,int roomID) throws SQLException
    {
    Connection con = Konekcija.VratiKonekciju();
    PreparedStatement st = con.prepareStatement("select 1 from room_type r , hotel h , users u where r.hotel_id = h.hotel_id and h.userId =  u.user_id and u.user_id = ? and room_type_id = ?;");
    st.setInt(1, idKOrisnik);
    st.setInt(2, roomID);
    ResultSet rs = st.executeQuery();
    boolean exists = rs.next(); // Check if a record exists
    con.close();
    return exists;
    }
    public static ArrayList<RoomType> returnFreeTypes(Connection con , String from  , String to , int hotelId) throws Exception
    {
        ArrayList<RoomType> roomTypes = new ArrayList<>();
        PreparedStatement st = con.prepareStatement("SELECT t.room_type_id, t.no_beds, t.room_type_name, t.desribe,t.banovan,t.hotel_id, t.PRICE " +
"FROM hotel h " +
"JOIN room_type t ON h.hotel_id = t.hotel_id " +
"JOIN room r ON t.room_type_id = r.room_type_id " +
"LEFT JOIN reservations re ON r.room_id = re.room_id AND re.dateFrom <= ? AND re.dateTo >= ? " +
"WHERE h.banovan = 1 " +
"AND t.banovan = 1 " +
"AND r.banovan = 1 " +
"AND h.hotel_id = ? " +
"AND re.room_id IS NULL " +
"GROUP BY t.room_type_id, t.no_beds, t.room_type_name, t.desribe, t.PRICE;");
        st.setString(1, to);
        st.setString(2, from);
        st.setInt(3, hotelId);
        ResultSet rs = st.executeQuery();
         while(rs.next())
        {
                    int no_beds = rs.getInt("no_beds");
                    int room_type_id = rs.getInt("room_type_id");
                    String room_type_name = rs.getString("room_type_name");
                    int hotel_id  = rs.getInt("hotel_id");
                    boolean banovan = rs.getBoolean("banovan");
                    String desribe = rs.getString("desribe");
                    int price = rs.getInt("price");
                    RoomType room = new RoomType(room_type_id,no_beds,room_type_name,desribe,hotel_id,banovan,price);
                    roomTypes.add(room);
        }
        return roomTypes;
    }
}
