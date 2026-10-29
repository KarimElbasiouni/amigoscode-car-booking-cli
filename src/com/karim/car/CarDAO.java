package com.karim.car;

import java.util.List;
import java.util.UUID;

public interface CarDAO {

    public List<Car> selectElectricCars();

    public Car selectCarById(UUID carId);

    public List<Car>  selectAllCars();
}
