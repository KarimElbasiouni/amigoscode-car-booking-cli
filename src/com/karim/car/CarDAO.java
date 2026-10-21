package com.karim.car;

import java.util.UUID;

public interface CarDAO {

    public Car[] selectElectricCars();

    public Car selectCarById(UUID carId);

    public Car[] selectAllCars();
}
