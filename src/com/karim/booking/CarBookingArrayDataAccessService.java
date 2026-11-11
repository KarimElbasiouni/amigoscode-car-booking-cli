package com.karim.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO {
    private static final List<CarBooking> carBookings;

    static{
        carBookings = new ArrayList<>();
    }

    @Override
    public void saveCarBooking(CarBooking carBooking){
        carBookings.add(carBooking);
    }

    @Override
    public void deleteCarBooking(CarBooking carBooking){
        carBooking.setStatus(BookingStatus.CANCELLED);
    }

    @Override
    public List<CarBooking>  selectAllCarBookings(){
        return new ArrayList<>(carBookings);
    }

    @Override
    public List<CarBooking>  selectCarBookingsByUserId(UUID userId){
        return carBookings
                .stream()
                .filter(carBooking -> userId.equals(carBooking.getUser().getId()))
                .toList();
    }

    @Override
    public Optional<CarBooking> selectCarBookingById(UUID carBookingId){
        return carBookings
                .stream()
                .filter(carBooking -> carBookingId.equals(carBooking.getId()))
                .findFirst();
    }
}

