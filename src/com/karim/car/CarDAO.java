package com.karim.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDAO {

    Optional<Car> selectCarById(UUID carId);

    List<Car>  selectAllCars();
}
