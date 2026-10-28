package com.karim.car;

import java.util.List;
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

    public List<Car> getElectricCars() throws RuntimeException{
        List<Car> electricCars = carDao.selectElectricCars();
        if (!electricCars.isEmpty()){
            return electricCars;
        }
        throw new RuntimeException("No electric cars in database");
    }

    public List<Car> getAllCars(){
        // no checks since there is No CRUD functionality for Cars
        return carDao.selectAllCars();
    }

}
