package com.karim.car;

import java.util.UUID;

public class CarService {
    private final CarDAO carDao;

    public CarService(CarDAO carDao) {
        this.carDao = carDao;
    }

    public Car getCarById(UUID id) throws RuntimeException{
        Car car = carDao.selectCarById(id);
        if(car==null){
            throw new RuntimeException("Car does not exist");

        }
        return car;
    }

    public Car[] getElectricCars() throws RuntimeException{
        Car[] electricCars = trimCarArray(carDao.selectElectricCars());
        if (electricCars.length > 0){
            return electricCars;
        }
        throw new RuntimeException("No electric cars in database");
    }

    public Car[] getAllCars(){
        // no checks since there is No CRUD functionality for Cars
        return carDao.selectAllCars();
    }


    private Car[] trimCarArray(Car[] cars){
        int numOfNonNull = 0;
        for (Car car : cars) {
            if(car!=null){
                numOfNonNull++;
            }
        }

        Car[] trimmed =  new Car[numOfNonNull];
        int trimmedIdx = 0;
        for (Car car : cars) {
            if(car!=null){
                trimmed[trimmedIdx++] = car;
            }
        }
        return trimmed;
    }
}
