package com.karim.car;

import java.util.List;
import java.util.UUID;

public class CarService {
    private final CarDAO carDao;

    public CarService(CarDAO carDao) {
        this.carDao = carDao;
    }

    public Car getCarById(UUID id) throws RuntimeException{
        return carDao.selectCarById(id)
                .orElseThrow(()-> new RuntimeException("Car does not exist"));
    }

    public List<Car> getAllCars(){
        return carDao.selectAllCars();
    }

}
