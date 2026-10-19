package com.karim.booking;

import com.karim.car.Car;
import com.karim.car.CarService;
import com.karim.user.User;
import com.karim.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    private final UserService userService;
    private final CarService carService;
    private final CarBookingDAO carBookingDao;

    public CarBookingService(UserService userService, CarService carService, CarBookingDAO carBookingDao){
        this.userService = userService;
        this.carService = carService;
        this.carBookingDao = carBookingDao;
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) throws RuntimeException{
        // validation checks
        User user = userService.getUserById(userId);
        Car car = carService.getCarById(carId);
        validateDates(startDate,endDate);

        CarBooking[] allBookings = carBookingDao.selectAllCarBookings();
        checkCarAvailability(allBookings, carId);

        //checks passed; book car
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal totalPrice = car.getRentalPricePerDay().
                multiply(BigDecimal.valueOf(numOfDays));
        LocalDateTime bookedAt = LocalDateTime.now();

        CarBooking newBooking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                startDate,
                endDate,
                totalPrice,
                BookingStatus.ACTIVE,
                bookedAt
        );

        carBookingDao.saveCarBooking(newBooking);
        System.out.println("Car booked successfully! " + totalPrice + "$ has been received from your account." );
        return newBooking;
    }

    public void deleteCarBookingById(UUID carBookingId) throws RuntimeException{
        CarBooking carBooking = carBookingDao.selectCarBookingById(carBookingId);
        if (carBooking == null){
            throw new IllegalArgumentException("No such car booking exists. Please enter a valid Car Booking Id");
        }
        if (carBooking.getStatus().equals(BookingStatus.CANCELLED)){
            throw new RuntimeException("Booking Already Deleted: Booking was already cancelled previously");
        }
        carBookingDao.deleteCarBooking(carBooking);
    }

    public CarBooking[] getUserBookings(UUID userId) throws RuntimeException{
        User user = userService.getUserById(userId);
        if(user==null) {
            throw new RuntimeException("User does not exist");
        }
        CarBooking[] trimmedCarBookings = trimCarBookingsArray(
                carBookingDao.selectCarBookingsByUserId(userId)
        );

        if(trimmedCarBookings.length > 0){
            return trimmedCarBookings;
        }
        throw new RuntimeException("No Bookings have been made by " + user.toString());
    }

    public CarBooking[] getAllBookings() throws RuntimeException {
        CarBooking[] trimmedCarBookings = trimCarBookingsArray(
                carBookingDao.selectAllCarBookings()
        );

        if(trimmedCarBookings.length > 0){
            return trimmedCarBookings;
        }
        throw new RuntimeException("No Bookings have been made.");
    }

    public Car[] getAvailableCars(){
        Car[] allCars = carService.getAllCars();
        CarBooking[] carBookings = carBookingDao.selectAllCarBookings();

        Car[] availableCars = new Car[allCars.length];
        int idx = 0;
        outerLoop:
        for (Car car : allCars) {
            for (CarBooking carBooking : carBookings) {
                if(car.equals(carBooking.getCar())
                        && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                    continue outerLoop;
                }
            }
            availableCars[idx++] = car;
        }
        return trimCarsArray(availableCars);
    }

    public Car[] getAvailableElectricCars(){
        Car[] allElectricCars = carService.getElectricCars();
        CarBooking[] carBookings = carBookingDao.selectAllCarBookings();

        Car[] availableElectricCars = new Car[allElectricCars.length];
        int idx = 0;
        outerLoop:
        for (Car car : allElectricCars) {
            for (CarBooking carBooking : carBookings) {
                if(car.equals(carBooking.getCar())
                        && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                    continue outerLoop;
                }
            }
            availableElectricCars[idx++] = car;
        }
        return trimCarsArray(availableElectricCars);


    }

    private void validateDates(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException{
        LocalDate currentDate = LocalDate.now();

        if(startDate.isBefore(currentDate)){
            throw new IllegalArgumentException("Invalid Date Choice: Start Date must not be in the past");
        }
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid Date Choice: Start Date must not be after End Date");
        }
    }

    private void checkCarAvailability(CarBooking[] carBookings, UUID carId) throws RuntimeException {

        for (CarBooking booking : carBookings) {
            if(booking == null){
                continue;
            }
            if (booking.getCar().getId().equals(carId) &&
                    booking.getStatus().equals(BookingStatus.ACTIVE)) {
                throw new RuntimeException("Car is not available");
            }
        }

    }

    //Note: Could potentially use utils folder to make a trimArray(Object obj) util function to trim all arrays (User, Car, CarBooking)
    private CarBooking[] trimCarBookingsArray(CarBooking[] carBookings){
        int numOfNonNull = 0;
        for (CarBooking carBooking : carBookings) {
            if(carBooking != null){
                numOfNonNull++;
            }
        }

        CarBooking[] trimmed =  new CarBooking[numOfNonNull];
        int trimmedIdx = 0;
        for (CarBooking carBooking : carBookings) {
            if(carBooking != null){
                trimmed[trimmedIdx++] = carBooking;
            }
        }
        return trimmed;
    }

    private Car[] trimCarsArray(Car[] cars){
        int numOfNonNull = 0;
        for (Car car : cars) {
            if(car != null){
                numOfNonNull++;
            }
        }

        Car[] trimmed =  new Car[numOfNonNull];
        int trimmedIdx = 0;
        for (Car car : cars) {
            if(car != null){
                trimmed[trimmedIdx++] = car;
            }
        }
        return trimmed;
    }
}
