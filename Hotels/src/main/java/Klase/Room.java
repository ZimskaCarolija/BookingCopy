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
public class Room {
    private int room_id;
    private int room_number;
    private String roomName;
    private boolean showing;
    private int room_type_id;

    public Room(int room_id, int room_number, String roomName, boolean showing, int room_type_id) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.roomName = roomName;
        this.showing = showing;
        this.room_type_id = room_type_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }
    public static ArrayList<Room> RoomHotel(int idHotel) throws SQLException
    {
        ArrayList<Room> rooms  = new ArrayList<Room>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select r.room_id,r.room_number,r.room_name,r.banovan,r.room_type_id from room r, room_type t , hotel h where r.room_type_id = t.room_type_id and t.hotel_id = h.hotel_id and h.hotel_id = ?");
        st.setInt(1, idHotel);
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int room_id = rs.getInt("room_id");
            int room_number = rs.getInt("room_number");
            String room_name = rs.getString("room_name");
            boolean banovan = rs.getBoolean("banovan");
            int room_type_id = rs.getInt("room_type_id");
            Room r = new Room(room_id,room_number,room_name,banovan,room_type_id);
            rooms.add(r);
        
        }
        return rooms;
    }
    public static Room RoomByID(int roomId) throws Exception
    {
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from room where room_id =?;");
        st.setInt(1, roomId);
        ResultSet rs = st.executeQuery();
        while(rs.next())
        {
            int room_id = rs.getInt("room_id");
            int room_number = rs.getInt("room_number");
            String room_name = rs.getString("room_name");
            boolean banovan = rs.getBoolean("banovan");
            int room_type_id = rs.getInt("room_type_id");
            Room r = new Room(room_id,room_number,room_name,banovan,room_type_id);
            return r;
        }
        throw new Exception("Not Found");
    }
    
}
