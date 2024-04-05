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
public class RoomTypeImage {
    private int room_img_id;
    private int room_type_id;
    private String image;

    public RoomTypeImage(int room_img_id, int room_type_id, String image) {
        this.room_img_id = room_img_id;
        this.room_type_id = room_type_id;
        this.image = image;
    }

    public int getRoom_img_id() {
        return room_img_id;
    }

    public void setRoom_img_id(int room_img_id) {
        this.room_img_id = room_img_id;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public static ArrayList<RoomTypeImage> LoadImages(int room_id) throws SQLException
    {
        ArrayList<RoomTypeImage> images  = new ArrayList<>();
        Connection con = Konekcija.VratiKonekciju();
        PreparedStatement st = con.prepareStatement("select * from room_type_image where room_type_id = ?");
        st.setInt(1, room_id);
        ResultSet rs =  st.executeQuery();
        while(rs.next())
        {
            int img_id = rs.getInt("room_img_id");
            int roomID = rs.getInt("room_type_id");
            byte[] imageBin  = rs.getBytes("image");
            String imageBase64 = Base64.getEncoder().encodeToString(imageBin);
            RoomTypeImage roomI = new RoomTypeImage(img_id,roomID,imageBase64);
            images.add(roomI);
        }
        return images;
    }
}
