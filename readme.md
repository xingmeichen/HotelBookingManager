### A simple Hotel Booking Manager

#### Environment Requirement
1. Jdk1.8 or greater
2. Maven 3

#### API description
You are able to Create a HotelBookingManager object by calling HotelBookingManager.getHotelBookingManager()
- bookRoom(String guestName, int roomNumber, Date) method is to store a booking
- listAvailableRoomsOnGivenDate(Date date) method is to find available rooms on a given date
- listHistoricalBookingByGuestName(String guestName) method is to find all the bookings for a given guest

- You can also call configRoomCount(int count) to config the number of room

#### Build project
mvn package