/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Klase;

/**
 *
 * @author Aleksa
 */
public class RoomTypeHotel {
    private RoomType room;
    private String HotelName;

    public RoomTypeHotel(RoomType room, String HotelName) {
        this.room = room;
        this.HotelName = HotelName;
    }

    public RoomType getRoom() {
        return room;
    }

    public void setRoom(RoomType room) {
        this.room = room;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String HotelName) {
        this.HotelName = HotelName;
    }
    
}
