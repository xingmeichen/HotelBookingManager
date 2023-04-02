package com.hbm;

import java.util.*;

/**
 * @project: HotelBookingManager
 * @description:
 * @author: Mabel.Chen
 * @create: 2023-03-31
 **/
public enum HotelBookingManager {

    HOTEL_BOOKING_MANAGER;

    public final static Map<String, Set<Integer>> mapBookedRoomByDate = new HashMap<>();
    public final static Map<String, List<Booking>> mapBookingByGuest = new HashMap<>();
    public static Integer roomCount = 10;

    /***
     * To get HOTEL_BOOKING_MANAGER
     * @return
     */
    public static HotelBookingManager getHotelBookingManager() {
        return HOTEL_BOOKING_MANAGER;
    }

    /***
     * To config the number of room
     * @param count
     */
    public void configRoomCount(int count) {
        if (count < 1 || count > Integer.MAX_VALUE) {
            throw new RuntimeException("The number of room should be in [1, 2147483647]");
        }
        synchronized (this) {
            roomCount = count;
        }
    }

    /***
     * To store a booking
     * @param guestName
     * @param roomNumber
     * @param date
     */
    public void bookRoom(String guestName, int roomNumber, Date date) {
        if (null == guestName || guestName.trim().isEmpty()) {
            throw new RuntimeException("Invalid guest name");
        }
        if (null == date) {
            throw new RuntimeException("Invalid date");
        }
        if (Utils.isBeforeDate(new Date(), date)) {
            throw new RuntimeException("Sorry, you are not able to book a room for past");
        }
        if (roomNumber < 1) {
            throw new RuntimeException("Invalid room number");
        }
        synchronized (this) {
            while (roomCount < roomNumber) {
                throw new RuntimeException("Invalid room number");
            }
            while (!isAvailable(roomNumber, date)) {
                throw new RuntimeException("The room is not available");
            }
            String dateStr = Utils.format(date);
            Booking booking = new Booking(guestName, roomNumber, date);
            Set<Integer> bookedRooms = mapBookedRoomByDate.getOrDefault(dateStr, new HashSet<>());
            bookedRooms.add(roomNumber);
            mapBookedRoomByDate.put(dateStr, bookedRooms);
            List<Booking> bookings = mapBookingByGuest.getOrDefault(guestName, new ArrayList<>());
            bookings.add(booking);
            mapBookingByGuest.put(guestName, bookings);
        }
    }

    /***
     * To find available rooms on a given date
     * @param date
     * @return
     */
    public Set<Integer> listAvailableRoomsOnGivenDate(Date date) {
        if (null == date) {
            throw new RuntimeException("Invalid date");
        }
        synchronized (this) {
            Set<Integer> bookedRooms = mapBookedRoomByDate.getOrDefault(Utils.format(date), new HashSet<>());
            if (bookedRooms.isEmpty()) {
                return initAvailableRooms();
            }
            Set<Integer> availableRooms = new HashSet<>();
            for (int i = 1; i <= roomCount; i++) {
                if (bookedRooms.contains(i)) {
                    continue;
                }
                availableRooms.add(i);
            }
            return availableRooms;
        }
    }

    /***
     * To find all the bookings for a given guest
     * @param guestName
     * @return
     */
    public List<Booking> listHistoricalBookingByGuestName(String guestName) {
        if (null == guestName || guestName.trim().isEmpty()) {
            throw new RuntimeException("Invalid guest name");
        }
        return mapBookingByGuest.getOrDefault(guestName, new ArrayList<>());
    }

    /***
     * To check a given room is available or not on a given date
     * @param roomNumber
     * @param date
     * @return
     */
    private boolean isAvailable(int roomNumber, Date date) {
        Set<Integer> bookedRooms = mapBookedRoomByDate.getOrDefault(Utils.format(date), new HashSet<>());
        return null == bookedRooms || bookedRooms.isEmpty() || !bookedRooms.contains(roomNumber);
    }

    /***
     * To initialize available rooms
     * @return
     */
    private Set<Integer> initAvailableRooms() {
        int index = 1;
        Set<Integer> availableRooms = new HashSet<>();
        while (index <= roomCount) {
            availableRooms.add(index);
            index++;
        }
        return availableRooms;
    }
}