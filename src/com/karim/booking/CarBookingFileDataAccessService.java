package com.karim.booking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO {
    private int numOfBookings;
    private final File numOfBookingsFile;
    private final File bookingsFile;
    private final File tempWritingFile;

    {
        bookingsFile = new File("bookings.dat");
        numOfBookingsFile = new File("numOfBookings.dat");

        try {
            if(!bookingsFile.exists()){
                bookingsFile.createNewFile();
            }
            if(!numOfBookingsFile.exists() || numOfBookingsFile.length() == 0){
                numOfBookingsFile.createNewFile();
            }else {
                ObjectInputStream loadCount = new ObjectInputStream(
                        new FileInputStream(numOfBookingsFile));
                numOfBookings = (int) loadCount.readObject();
                loadCount.close();
            }

            tempWritingFile = new File("tempWriting.dat");
            tempWritingFile.createNewFile();
            tempWritingFile.deleteOnExit();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void saveCarBooking(CarBooking carBooking) {

        try (ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(tempWritingFile));
             ObjectOutputStream saveCount = new ObjectOutputStream(
                     new FileOutputStream(numOfBookingsFile));
        )
        {
            try (ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream(bookingsFile)))
            {
                while(true){
                    CarBooking currentBooking = (CarBooking) input.readObject();
                    output.writeObject(currentBooking);
                }
            }catch (EOFException _){

            }
            output.writeObject(carBooking);
            numOfBookings++;
            saveCount.writeObject(numOfBookings);
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.copy(Path.of(tempWritingFile.getPath()), Path.of(bookingsFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteCarBooking(CarBooking carBooking) {

        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(tempWritingFile));)
        {
            try (ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(bookingsFile)))
            {

                while (true) {
                    CarBooking currentBooking = (CarBooking) input.readObject();
                    if (currentBooking.equals(carBooking)) {
                        currentBooking.setStatus(BookingStatus.CANCELLED);
                    }
                    output.writeObject(currentBooking);
                }
            }catch(EOFException _){

            }
        }  catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            Files.copy(Path.of(tempWritingFile.getPath()), Path.of(bookingsFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<CarBooking> selectAllCarBookings() {
        List<CarBooking> allCarBookings = new ArrayList<>();
        try(
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream(bookingsFile))
        ){

            for (int i = 0; i < numOfBookings; i++) {
                CarBooking currentBooking = (CarBooking) input.readObject();
                allCarBookings.add(currentBooking);
            }
            return allCarBookings;

        }catch (EOFException e){
            return allCarBookings;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<CarBooking> selectCarBookingsByUserId(UUID userId) {
        List<CarBooking> userBookings =  new ArrayList<>();
        try (
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream(bookingsFile))
        ){

            while(true) {
                CarBooking currentBooking = (CarBooking) input.readObject();
                if(userId.equals(currentBooking.getUser().getId())){
                    userBookings.add(currentBooking);
                }
            }

        } catch(EOFException e){
            return userBookings;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CarBooking selectCarBookingById(UUID carBookingId) {
        try (
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream(bookingsFile)))
        {

            while(true) {
                CarBooking currentBooking = (CarBooking) input.readObject();
                if(carBookingId.equals(currentBooking.getId())){
                    return currentBooking;
                }
            }

        } catch (EOFException e) {
            return null;

        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
}
