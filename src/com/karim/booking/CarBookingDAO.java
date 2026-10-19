package com.karim.booking;

import java.util.UUID;

public interface CarBookingDAO {


    void saveCarBooking(CarBooking carBooking);

    void deleteCarBooking(CarBooking carBooking);

    CarBooking[] selectAllCarBookings();

    CarBooking[] selectCarBookingsByUserId(UUID userId);

    CarBooking selectCarBookingById(UUID carBookingId);


}
