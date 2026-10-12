package com.karim.booking;

import java.util.UUID;

public class CarBookingDAO {
    private static CarBooking[] carBookings;
    private final static int CAPACITY = 50;
    private int nextAvailableSlot;

    static{
        carBookings = new CarBooking[CAPACITY];
    }

    public void saveCarBooking(CarBooking carBooking){
        carBookings[nextAvailableSlot++] = carBooking;
    }

    public void deleteCarBooking(CarBooking carBooking){
        carBooking.setStatus(BookingStatus.CANCELLED);
    }

    public CarBooking[] selectAllCarBookings(){
        return carBookings;
    }

    public CarBooking[] selectCarBookingsByUserId(UUID userId){

        CarBooking[] userCarBooking = new CarBooking[CAPACITY];
        int idx = 0;
        for (CarBooking carBooking : carBookings) {
            if(carBooking != null) {
                if(userId.equals(carBooking.getUser().getId())){
                    userCarBooking[idx++] = carBooking;
                }
            }
        }
        return userCarBooking;
    }

    public CarBooking selectCarBookingById(UUID carBookingId){
        for (CarBooking carBooking : carBookings) {
            if(carBookingId.equals(carBooking.getId())){
                return carBooking;
            }
        }
        return null;
    }


}
