package com.karim.booking;

import java.util.ArrayList;
import java.util.List;
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

        List<CarBooking> userCarBookings = new ArrayList<>();

        carBookings.forEach(carBooking -> {
            if (userId.equals(carBooking.getUser().getId())){
                userCarBookings.add(carBooking);
            }
        });
        return userCarBookings;
    }

    @Override
    public CarBooking selectCarBookingById(UUID carBookingId){

        for(CarBooking carBooking: carBookings){
            if (carBookingId.equals(carBooking.getId())){
                return carBooking;
            }
        }

        return null;
    }
}

