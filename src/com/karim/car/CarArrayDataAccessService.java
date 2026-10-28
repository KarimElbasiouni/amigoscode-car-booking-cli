package com.karim.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDAO {

    private static final List<Car> cars;

    static {
        cars = new ArrayList<>();
        cars.add(new Car(UUID.fromString("4d81bb82-586d-48fc-aca6-100d26d948d4"), "C0001",
                new BigDecimal(90), Brand.TESLA, true));
        cars.add(new Car(UUID.fromString("95108973-4512-4c7e-83d4-60e6ad812ef1"), "C0002",
                new BigDecimal(80), Brand.TOYOTA, false));
        cars.add( new Car(UUID.fromString("e1a0a937-73a6-4d05-ab22-8ec14dc805f9"), "C0003",
                new BigDecimal(110), Brand.MERCEDES, false));
        cars.add(new Car(UUID.fromString("ae71d0cd-7b1c-4be2-9712-751098d871c5"), "C0004",
                new BigDecimal(100), Brand.AUDI, false));
        cars.add(new Car(UUID.fromString("ed59f91f-e983-4696-9998-ce16567589b6"), "C0005",
                new BigDecimal(120), Brand.MERCEDES, true));

    };




    public List<Car> selectElectricCars(){
        List<Car> electricCars = new ArrayList<>();
        for (Car car : cars) {

            if(car.isElectric()) {
                electricCars.add(car);
            }

        }
        return electricCars;
    }

    public Car selectCarById(UUID carId){
        for (Car car : cars) {
            if(car.getId().equals(carId)){
                return car;
            }
        }
        return null;
    }

    public List<Car> selectAllCars(){
        return cars;
    }
}

