package com.karim;

import com.karim.booking.CarBookingService;
import com.karim.car.CarService;
import com.karim.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private final static CarBookingService bookingService = new CarBookingService();
    private final static CarService carService = new CarService();
    private final static UserService userService = new UserService();
    private final static Scanner scanner = new Scanner(System.in);


    static void main(String[] args) throws RuntimeException{


        while(true) {
            System.out.println("Welcome to the Car Booking System! Select a menu item from the main menu by typing its corresponding number");
            System.out.println("Main Menu:");
            System.out.println("1 - Book a Car");
            System.out.println("2 - Delete Booking");
            System.out.println("3 - View User Bookings");
            System.out.println("4 - View All Bookings");
            System.out.println("5 - View Available Cars");
            System.out.println("6 - View Electric Cars");
            System.out.println("7 - View All Users");
            System.out.println("8 - Exit");
            System.out.print("Menu Item Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch(choice){
                    case 1 -> handleBookACarPrompt();
                    case 2 -> handleDeleteBookingPrompt();
                    case 3 -> handleViewUserBookingPrompt();
                    case 4 -> handleViewAllBookingsPrompt();
                    case 5 -> handleViewAvailableCarsPrompt();
                    case 6 -> handleViewElectricCarsPrompt();
                    case 7 -> handleViewAllUsersPrompt();
                    case 8 -> {
                        System.out.println("Exiting the application...");
                        return;
                    }
                    default -> throw new RuntimeException("Invalid choice. Please choose 1-8!");
                }
            } catch (RuntimeException e) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println("-------------------------------------");
                System.out.println("Restarting Application...");
                System.out.println();
            }
        }

    }

    private static void handleBookACarPrompt() throws DateTimeParseException{
        System.out.println("Enter your User ID (Hint: Select '7' from the menu item " +
                "OR go to UserDAO and copy-paste one of the available IDs):");
        UUID userId = UUID.fromString(scanner.nextLine());

        System.out.println("Enter the Car ID (Hint: Select '5' from the menu item" +
                "OR go to CarDAO and copy-paste one of the available IDs):");
        UUID carId = UUID.fromString(scanner.nextLine());
        System.out.println("Enter your desired Start Date in the format 'yyyy-MM-dd':");
        LocalDate startDate;
        LocalDate endDate;
        while (true){
            try {
                startDate = LocalDate.parse(scanner.nextLine());
                break;
            }catch(DateTimeParseException e){
                System.out.println("Invalid Date Format");
            }

        }
        System.out.println("Enter your desired End Date in the format 'yyyy-MM-dd':");
        while (true){
            try {
                endDate = LocalDate.parse(scanner.nextLine());
                break;
            }catch(DateTimeParseException e){
                System.out.println("Invalid Date Format");
            }
        }
        bookingService.bookCar(userId,carId,startDate,endDate);
    }

    private static void handleDeleteBookingPrompt(){
        System.out.println("Please enter the Booking ID of the booking you'd like to delete:");
        UUID carBookingId = UUID.fromString(scanner.nextLine());
        bookingService.deleteCarBookingById(carBookingId);
        System.out.println("Booking deleted successfully");
    }
    private static void handleViewUserBookingPrompt(){
        System.out.println("Please enter the ID for the desired user:");
        UUID userId = UUID.fromString(scanner.nextLine());
        System.out.println("User Bookings for User: " +
                Arrays.toString(
                        bookingService.getUserBookings(userId))
        );
    }
    private static void handleViewAllBookingsPrompt(){
        System.out.println("Retrieving all bookings...");

        System.out.println(Arrays.toString(
                bookingService.getAllBookings())
        );
    }

    private static void handleViewAvailableCarsPrompt(){
        System.out.println("Retrieving available cars...");

        System.out.println(Arrays.toString(
                bookingService.getAvailableCars())
        );
    }
    private static void handleViewElectricCarsPrompt(){
        System.out.println("Retrieving available electric cars...");

        System.out.println(Arrays.toString(
                bookingService.getAvailableElectricCars())
        );
    }
    private static void handleViewAllUsersPrompt(){
        System.out.println("Retrieving users...");

        System.out.println(Arrays.toString(
                userService.getAllUsers())
        );
    }
}


