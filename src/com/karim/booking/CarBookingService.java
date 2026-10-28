package com.karim.booking;

import com.karim.car.Car;
import com.karim.car.CarService;
import com.karim.user.User;
import com.karim.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

        List<CarBooking> allBookings = carBookingDao.selectAllCarBookings();
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

    public List<CarBooking> getUserBookings(UUID userId) throws RuntimeException{
        User user = userService.getUserById(userId);
        if(user==null) {
            throw new RuntimeException("User does not exist");
        }
        List<CarBooking> userCarBookings = carBookingDao.selectCarBookingsByUserId(userId);

        if(!userCarBookings.isEmpty()){
            return carBookingDao.selectCarBookingsByUserId(userId);
        }
        throw new RuntimeException("No Bookings have been made by " + user.toString());
    }

    public List<CarBooking> getAllBookings() throws RuntimeException {
        List<CarBooking> carBookings = carBookingDao.selectAllCarBookings();

        if(!carBookings.isEmpty()){
            return carBookingDao.selectAllCarBookings();
        }
        throw new RuntimeException("No Bookings have been made.");
    }

    public List<Car>getAvailableCars(){
        List<Car> allCars = carService.getAllCars();
        List<CarBooking> carBookings = carBookingDao.selectAllCarBookings();

        List<Car> availableCars = new ArrayList<>();

        outerLoop:
        for (Car car : allCars) {
            for (CarBooking carBooking : carBookings) {
                if(car.equals(carBooking.getCar())
                        && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                    continue outerLoop;
                }
            }
            availableCars.add(car);
        }
        return availableCars;
    }

    public List<Car> getAvailableElectricCars(){
        List<Car> allElectricCars = carService.getElectricCars();
        List<CarBooking> carBookings = carBookingDao.selectAllCarBookings();

        List<Car> availableElectricCars = new ArrayList<>();

        outerLoop:
        for (Car car : allElectricCars) {
            for (CarBooking carBooking : carBookings) {
                if(car.equals(carBooking.getCar())
                        && carBooking.getStatus().equals(BookingStatus.ACTIVE)) {
                    continue outerLoop;
                }
            }
            availableElectricCars.add(car);
        }
        return availableElectricCars;
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

    private void checkCarAvailability(List<CarBooking> carBookings, UUID carId) throws RuntimeException {

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

}
