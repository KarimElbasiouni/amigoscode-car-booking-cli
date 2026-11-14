package com.karim.car;

import com.karim.user.User;
import net.datafaker.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarFakerDataAccessService implements CarDAO{

    private static final List<Car> cars;
    static {
        cars = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            cars.add(
                    new Car(
                            UUID.randomUUID(),
                            faker.numerify("C####"),
                            new BigDecimal(Math.random()*100 + 50),
                            faker.options().option(Brand.class),
                            faker.bool().bool()
                    ));
        }
    }

    @Override
    public Optional<Car> selectCarById(UUID carId) {
        return cars.stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst();
    }

    @Override
    public List<Car> selectAllCars() {
        return new ArrayList<>(cars);
    }
}
