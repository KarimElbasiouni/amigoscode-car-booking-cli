package com.karim.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDAO {


    void saveCarBooking(CarBooking carBooking);

    void deleteCarBooking(CarBooking carBooking);

    List<CarBooking> selectAllCarBookings();

    List<CarBooking>  selectCarBookingsByUserId(UUID userId);

    CarBooking selectCarBookingById(UUID carBookingId);


}
