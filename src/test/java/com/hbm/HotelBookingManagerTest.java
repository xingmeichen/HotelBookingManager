package com.hbm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

/**
 * @project: HotelBookingManager
 * @description:
 * @author: Mabel.Chen
 * @create: 2023-03-31
 **/
public class HotelBookingManagerTest {

    private static HotelBookingManager hotelBookingManager;

    @BeforeEach
    public void initRoomCount() {
        hotelBookingManager = HotelBookingManager.getHotelBookingManager();
        hotelBookingManager.configRoomCount(100);
    }

    @Test
    public void getHotelBookingManagerTest() {
        HotelBookingManager hotelBookingManager = HotelBookingManager.getHotelBookingManager();
        Assertions.assertEquals(hotelBookingManager, hotelBookingManager);
    }

    @Test
    public void configRoomCountTest() {
        hotelBookingManager.configRoomCount(101);
        Assertions.assertEquals(101, hotelBookingManager.roomCount);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.configRoomCount(-1));
        Assertions.assertEquals("The number of room should be in [1, 2147483647]", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.configRoomCount(Integer.MAX_VALUE + 1));
        Assertions.assertEquals("The number of room should be in [1, 2147483647]", runtimeException.getMessage());
    }

    @Test
    public void bookRoomTest() {
        String guestName = "Guest1";
        int roomNumber = 1;
        Date date = new Date();
        String dateStr = Utils.format(date);
        Date past = new Date(date.getTime() - 24 * 1000 * 60 * 60);
        hotelBookingManager.bookRoom(guestName, roomNumber, date);
        Assertions.assertTrue(hotelBookingManager.mapBookedRoomByDate.containsKey(dateStr));
        Assertions.assertTrue(hotelBookingManager.mapBookedRoomByDate.get(dateStr).contains(roomNumber));
        Assertions.assertTrue(hotelBookingManager.mapBookingByGuest.containsKey(guestName));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom(guestName, roomNumber, date));
        Assertions.assertEquals("The room is not available", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("guest1", roomNumber, date));
        Assertions.assertEquals("The room is not available", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("VIP", roomNumber, date));
        Assertions.assertEquals("The room is not available", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom(null, 2, new Date()));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("", 2, new Date()));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("    ", 2, new Date()));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("Guest1", 2, null));
        Assertions.assertEquals("Invalid date", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("Guest1", 2, past));
        Assertions.assertEquals("Sorry, you are not able to book a room for past", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("Guest1", -1, new Date()));
        Assertions.assertEquals("Invalid room number", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.bookRoom("Guest1", Integer.MAX_VALUE + 1, new Date()));
        Assertions.assertEquals("Invalid room number", runtimeException.getMessage());
    }

    @Test
    public void listAvailableRoomsOnGivenDate() {
        Date date = new Date();
        Set<Integer> availableRooms = hotelBookingManager.listAvailableRoomsOnGivenDate(date);
        Assertions.assertNotNull(availableRooms);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.listAvailableRoomsOnGivenDate(null));
        Assertions.assertEquals("Invalid date", runtimeException.getMessage());
    }

    @Test
    public void listHistoricalBookingByGuestNameTest() {
        Assertions.assertNotNull(hotelBookingManager.listHistoricalBookingByGuestName("Guest1"));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.listHistoricalBookingByGuestName(null));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.listHistoricalBookingByGuestName(""));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());

        runtimeException = Assertions.assertThrows(RuntimeException.class, () -> hotelBookingManager.listHistoricalBookingByGuestName("    "));
        Assertions.assertEquals("Invalid guest name", runtimeException.getMessage());
    }
}