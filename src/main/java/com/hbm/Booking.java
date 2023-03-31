package com.hbm;

import java.util.Date;

/**
 * @project: HotelBookingManager
 * @description:
 * @author: Mabel.Chen
 * @create: 2023-03-31
 **/
public class Booking {

    private String guestName;
    private int roomNumber;
    private Date date;

    public Booking(String guestName, int roomNumber, Date date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}